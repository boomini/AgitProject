package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = -1286180108L;

<<<<<<< HEAD:backend-java/src/main/generated/com/ssafy/db/entity/QArticle.java
    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticle article = new QArticle("article");
=======
    public static final QBoard board = new QBoard("board");
>>>>>>> master:backend-java/src/main/generated/com/ssafy/db/entity/QBoard.java

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedDate = createDateTime("updatedDate", java.time.LocalDateTime.class);

<<<<<<< HEAD:backend-java/src/main/generated/com/ssafy/db/entity/QArticle.java
    public final QUser user;

    public final StringPath writer = createString("writer");

    public QArticle(String variable) {
        this(Article.class, forVariable(variable), INITS);
    }

    public QArticle(Path<? extends Article> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticle(PathMetadata metadata, PathInits inits) {
        this(Article.class, metadata, inits);
    }

    public QArticle(Class<? extends Article> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
=======
    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
>>>>>>> master:backend-java/src/main/generated/com/ssafy/db/entity/QBoard.java
    }

}

