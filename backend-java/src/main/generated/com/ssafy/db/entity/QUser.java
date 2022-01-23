package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 846542477L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

<<<<<<< HEAD
    public final ListPath<Article, QArticle> articles = this.<Article, QArticle>createList("articles", Article.class, QArticle.class, PathInits.DIRECT2);

=======
>>>>>>> master
    public final DateTimePath<java.time.LocalDateTime> cDate = createDateTime("cDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> day = createNumber("day", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath userId = createString("userId");

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

