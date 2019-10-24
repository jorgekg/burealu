package br.com.bureau.gateway.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 455499452L;

    public static final QUser user = new QUser("user");

    public final QAuditable _super = new QAuditable(this);

    //inherited
    public final DateTimePath<java.util.Date> createAt = _super.createAt;

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath password = createString("password");

    public final NumberPath<Integer> personId = createNumber("personId", Integer.class);

    public final ListPath<br.com.bureau.gateway.models.enums.Role, EnumPath<br.com.bureau.gateway.models.enums.Role>> roles = this.<br.com.bureau.gateway.models.enums.Role, EnumPath<br.com.bureau.gateway.models.enums.Role>>createList("roles", br.com.bureau.gateway.models.enums.Role.class, EnumPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.util.Date> updateAt = _super.updateAt;

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

