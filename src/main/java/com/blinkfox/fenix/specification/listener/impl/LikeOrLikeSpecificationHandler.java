package com.blinkfox.fenix.specification.listener.impl;

import com.blinkfox.fenix.exception.BuildSpecificationException;
import com.blinkfox.fenix.specification.annotation.LikeOrLike;
import com.blinkfox.fenix.specification.listener.AbstractSpecificationHandler;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Component;

/**
 * 构建“模糊条件”({@code field1 LIKE '%xx%' OR field2 LIKE '%yyy%'})场景的 Specification 监听器.
 *
 * @author YangWenpeng on 2019-12-17
 * @author blinkfox on 2020-01-14
 * @since v2.2.0
 */
@Component
public class LikeOrLikeSpecificationHandler extends AbstractSpecificationHandler {

    @SuppressWarnings("unchecked")
    @Override
    protected <Z, X> Predicate buildPredicate(
            CriteriaBuilder criteriaBuilder, From<Z, X> from, String name, Object value, Object annotation) {
        String[] fileds = ((LikeOrLike) annotation).fileds();
        List<Object> values = (List<Object>) value;
        int len = fileds.length;
        if (len != values.size()) {
            throw new BuildSpecificationException(
                    "【Fenix 异常】对【" + name + "】使用【@LikeOrLike】时，注解上【fields】长度和字段值的大小不同，fileds 长为:【"
                            + fileds.length + "】,字段值大小为：【" + values.size() + "】.");
        }

        List<Predicate> predicates = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            predicates.add(criteriaBuilder.like(from.get(fileds[i]),
                    "%" + values.get(i).toString().replace("%", "\\%") + "%"));
        }
        return criteriaBuilder.and(criteriaBuilder.or(predicates.toArray(new Predicate[0])));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<LikeOrLike> getAnnotation() {
        return LikeOrLike.class;
    }

}