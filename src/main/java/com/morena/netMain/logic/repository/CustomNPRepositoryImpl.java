package com.morena.netMain.logic.repository;

import com.morena.netMain.logic.entity.NotePosts;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.*;

public class CustomNPRepositoryImpl implements CustomNPRepository{

    @PersistenceContext
    private EntityManager entityManager;

    static final String where = " where ";
    static final String and = " and ";
    static final String or = " or ";

    private static final Map<String, String> patterns = Map.ofEntries(
            //NotePosts
            Map.entry("from","np.createdTimestamp >= "),
            Map.entry("to", "np.createdTimestamp <= "),
            Map.entry("scopes", "np.scope.code in "),
            Map.entry("inHead","upper(np.header) like "),
            Map.entry("inContent", "upper(np.content) like "),

            //NoteComments
            Map.entry("inComment", "upper(nc.content) like "),
            Map.entry("commentatorIds", "nc.author.uniqueId in "),

            //other
            Map.entry("selectNP", "from NotePosts np"),
            Map.entry("sort", " order by np.createdTimestamp desc"),

            Map.entry("extSelectNP", "from NotePosts pp where pp.uniqueId in (" +
                    "select distinct np.uniqueId from NotePosts np inner join NoteComments nc on np.uniqueId = nc.postRef"),
            Map.entry("extSort", ") order by pp.createdTimestamp desc")
    );

    /**
     * Parsing into select-sql of patterns:
     *
     * 1 - simple
     *    select entity from table where <conditions> order
     * 2 - complicated
     *    --external (select entity from table where entityId in --internal (join + <conditions>) order)
     *
     *    condition aliases are nc and np
     *    external alias pp
     */
    @Override
    public List<NotePosts> findAllByParsedRequest(LocalDate from, LocalDate to,
                                                  String label, Boolean inHead, Boolean inContent, Boolean inComment,
                                                  List<Long> scopes,
                                                  List<Long> commentatorIds) {

        boolean noAuthors = commentatorIds == null || commentatorIds.isEmpty();
        boolean noScopes = scopes == null || scopes.isEmpty();
        boolean inComments = label != null && inComment;
        boolean noJoin = noAuthors && !inComments;

        //case no-params request
        if(from==null && to==null && label==null && noScopes && noAuthors)
            return response(patterns.get("selectNP")+patterns.get("sort"));


        StringBuilder stringBuilder = new StringBuilder(noJoin ? patterns.get("selectNP") : patterns.get("extSelectNP"));
        List<String> conditions = new ArrayList<>();

        if(from != null)
            conditions.add(patterns.get("from") + from.atStartOfDay());

        if(to != null)
            conditions.add(patterns.get("to") + to.atTime(23,59,59));

        if(label != null){
            if(!inComment&&!inContent&&!inHead){}
            else  {
                label="\'%"+label.toUpperCase(Locale.ROOT)+"%\'";
                List<String> labelConditions = new ArrayList<>();
                if(inHead)
                    labelConditions.add(patterns.get("inHead")+label);
                if(inContent)
                    labelConditions.add(patterns.get("inContent")+label);
                if(inComment)
                    labelConditions.add(patterns.get("inComment")+label);

                for(int i = 0; i < labelConditions.size()-1; i+=2)
                    labelConditions.add(i+1,or);

                if(labelConditions.size()>1) {
                    labelConditions.add(0, "(");
                    labelConditions.add(")");
                }

                conditions.add(String.join("", labelConditions));
            }
        }

        if(!noAuthors)
            conditions.add(patterns.get("commentatorIds")+customToSting(commentatorIds));

        if(!noScopes)
            conditions.add(patterns.get("scopes")+customToSting(scopes));

        for(int i = 0; i < conditions.size()-1; i+=2)
            conditions.add(i+1,and);

        stringBuilder
                .append(where)
                .append(String.join("", conditions))
                .append(noJoin ? patterns.get("sort") : patterns.get("extSort"));

        return response(stringBuilder.toString());
    }

    private List<NotePosts> response(String sql){
        return entityManager
                .createQuery(sql, NotePosts.class)
                .getResultList();
    }

    private String customToSting(List<?> list){
        return "("+list.toString().substring(1, list.toString().length()-1)+")";
    }
}
