package br.com.bureau.gateway.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
	
	@Column(name = "create_at")
    @CreatedDate
    protected Date createAt;
    
    @Column(name = "update_at", nullable = true)
    @LastModifiedDate
    protected Date updateAt;
	
}
