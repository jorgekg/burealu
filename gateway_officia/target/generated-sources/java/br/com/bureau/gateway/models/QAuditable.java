package br.com.bureau.gateway.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAuditable is a Querydsl query type for Auditable
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QAuditable extends EntityPathBase<Auditable> {

    private static final long serialVersionUID = -1851029660L;

    public static final QAuditable auditable = new QAuditable("auditable");

    public final DateTimePath<java.util.Date> createAt = createDateTime("createAt", java.util.Date.class);

    public final DateTimePath<java.util.Date> updateAt = createDateTime("updateAt", java.util.Date.class);

    public QAuditable(String variable) {
        super(Auditable.class, forVariable(variable));
    }

    public QAuditable(Path<? extends Auditable> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditable(PathMetadata metadata) {
        super(Auditable.class, metadata);
    }

}

