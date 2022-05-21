package com.morena.netMain.logic.service;

import com.morena.netMain.auth.model.SysTokens;
import com.morena.netMain.auth.repository.SysTokensRepository;
import com.morena.netMain.logic.entity.QSysUsers;
import com.morena.netMain.logic.entity.SysUsers;
import com.morena.netMain.logic.model.dao.PSysUsers;
import com.morena.netMain.logic.model.filter.UserFilterRequest;
import com.morena.netMain.logic.model.builder.PSysUsersBuilder;
import com.morena.netMain.logic.repository.DictScopesRepository;
import com.morena.netMain.logic.repository.SysUsersRepository;
import com.morena.netMain.logic.utils.Pair;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.morena.netMain.auth.model.Role.*;

@Service
@RequiredArgsConstructor
public class SysUsersService implements RoleChecker{

    private final AuthService authService;
    private final SysUsersRepository sysUsersRepository;
    private final DictScopesRepository dictScopesRepository;
    private final SysTokensRepository sysTokensRepository;

    @Override
    public boolean isAdmin() {
        return authService.getCurrentUserRoles().stream().anyMatch(List.of(
                system,
                user_data_admin_viewer,
                user_data_admin_deleter)
                ::contains);
    }

    public Optional<SysUsers> getCurrentUser(){
        return sysUsersRepository.findOneByUniqueIdAndIsDeletedFalse(authService.getCurrentUserId());
    }

    public Optional<SysUsers> getUserByIdNotDeleted(Long id){
        return sysUsersRepository.findOneByUniqueIdAndIsDeletedFalse(id);
    }

    public Optional<SysUsers> getUserById(Long id){
        return sysUsersRepository.findOneByUniqueId(id);
    }

    public PSysUsers getByIdMapped(Long id){
        Optional<SysUsers> user = getUserById(id);
        if (user.isEmpty())
            return null;

        if(isAdmin())
            return PSysUsersBuilder.toAdminPojo(user.get());
        return PSysUsersBuilder.toUserPojo(user.get());
    }

    public PSysUsers getByIdMappedNotDeleted(Long id){
        Optional<SysUsers> user = getUserByIdNotDeleted(id);
        if (user.isEmpty())
            return null;

        if(isAdmin())
            return PSysUsersBuilder.toAdminPojo(user.get());
        return PSysUsersBuilder.toUserPojo(user.get());
    }

    public PSysUsers getCurrentMapped(){
        Optional<SysUsers> user = getCurrentUser();
        if (user.isEmpty())
            return null;

        if(isAdmin())
            return PSysUsersBuilder.toAdminPojo(user.get());
        return PSysUsersBuilder.toUserPojo(user.get());
    }

    public boolean updateAdditionalData (Pair userData){
        Optional<SysUsers> sysUsers = sysUsersRepository.findOneByUniqueIdAndIsDeletedFalse(userData.getValue());
        if (sysUsers.isEmpty())
            return false;

        sysUsers.get().setAbout(userData.getLabel());
        sysUsersRepository.save(sysUsers.get());
        return true;
    }

    public boolean updateByAdmin(PSysUsers puser){

        Optional<SysUsers> user = getUserById(puser.getId());
        if (user.isEmpty())
            return false;

        if(puser.getScope()!= null && puser.getScope().getValue()!= null &&
                !Objects.equals(puser.getScope().getValue(), user.get().getScope().getCode()))
            user.get().setScope(dictScopesRepository
                    .findOneByCodeAndIsDeletedIsFalse(puser.getScope().getValue()));

        if(puser.getIsDeleted() != null)
            user.get().setIsDeleted(puser.getIsDeleted());

        if(puser.getIsTokenAllowed() != null)
            banUser(user.get(), !puser.getIsTokenAllowed());

        return true;
    }

    public void deleteUser(Long id){
        sysUsersRepository.deleteById(id);
    }

    public boolean undeleteUser(Long id){
        Optional<SysUsers> user = sysUsersRepository.findOneByUniqueId(id);
        if(user.isEmpty())
            return false;
        user.get().setIsDeleted(false);
        sysUsersRepository.save(user.get());
        return true;
    }

    public boolean banUser(Long id, Boolean isBanned){
        Optional<SysUsers> user = getUserById(id);
        if (user.isEmpty())
            return false;

        banUser(user.get(), isBanned);
        return true;
    }

    public void banUser(SysUsers user, Boolean isBanned){

        SysTokens token = user.getToken();

        if(token == null) {
            sysTokensRepository.save(new SysTokens(
                    UUID.randomUUID(),
                    isBanned,
                    null,
                    user));
            return;
        }

        token.setIsDeleted(isBanned);
        sysUsersRepository.save(user);
    }

    public boolean changeScope(Long id, Long newScope){
        Optional<SysUsers> user = getUserById(id);
        if (user.isEmpty())
            return false;

        if(newScope < 1 ||
                newScope > dictScopesRepository.findTopByIsDeletedFalseOrderByCodeDesc().getCode())
            return false;

        user.get().setScope(dictScopesRepository.findOneByCodeAndIsDeletedIsFalse(newScope));
        sysUsersRepository.save(user.get());
        return true;
    }

    public List<PSysUsers> getAll(){
        return PSysUsersBuilder.toAdminPojoList(sysUsersRepository.findAllByOrderByLogin());
    }

    public Collection<PSysUsers> getFilteredUsers(UserFilterRequest request){

        List<SysUsers> users = new ArrayList<>();
        sysUsersRepository.findAll(predicateParse(request))
                .forEach(users::add);
        users.sort((Comparator.comparing(SysUsers::getLogin)));
        return PSysUsersBuilder.toAdminPojoList(users);
    }

    private BooleanExpression predicateParse(UserFilterRequest request) {
        List <BooleanExpression> conditions = new ArrayList<>();

        if (request.getFrom() != null)
            conditions.add(QSysUsers.sysUsers.createdTimestamp.after(request.getFrom().atStartOfDay()));

        if (request.getTo() != null)
            conditions.add(QSysUsers.sysUsers.createdTimestamp.before(request.getTo().atTime(23,59,59)));

        if (request.getLabel() != null)
            conditions.add(QSysUsers.sysUsers.login.containsIgnoreCase(request.getLabel()));

        if (request.getIsDeleted() != null)
            if(request.getIsDeleted())
                conditions.add(QSysUsers.sysUsers.isDeleted.isTrue());
            else
                conditions.add(QSysUsers.sysUsers.isDeleted.isFalse());

        if (request.getIsActive() != null)
            if(request.getIsActive())
                conditions.add(QSysUsers.sysUsers.isActive.isTrue());
            else
                conditions.add(QSysUsers.sysUsers.isActive.isFalse());

        if (request.getIsUser() != null)
            if(request.getIsUser())
                conditions.add(QSysUsers.sysUsers.isUser.isTrue());
            else
                conditions.add(QSysUsers.sysUsers.isUser.isFalse());

        if(request.getScopes() != null)
            conditions.add(QSysUsers.sysUsers.scope.code.in(request.getScopes()));

        BooleanExpression result = conditions.get(0);
        for(int i = 1; i < conditions.size(); ++i)
            result = result.and(conditions.get(i));

        return result;
    }
}