package com.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "marks")
@EntityListeners(AuditingEntityListener.class)
public class Marks {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long attachmentId;
	
	@NotBlank
	private String author;
	
	@Column(name = "email", nullable = false)
	@Email(message = "Please provide a valid e-mail")
	@NotEmpty(message = "Please provide an e-mail")
	private String email;
	
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
	private Date commentTime;
	
    @NotBlank
    private String markPara1;
    
    @NotBlank
    private String markPara2;
    
    @NotBlank
    private String markPara3;
    
    @NotBlank
    private String markPara4;
    
    @NotBlank
    private String markPara5;
    
	private Long marks;
	
	
    //@NotBlank
    //@Column(length=500)
	//private String remarks;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public Long getMarks() {
		return marks;
	}

	public void setMarks(Long marks) {
		this.marks = marks;
	}

	public String getMarkPara1() {
		return markPara1;
	}

	public void setMarkPara1(String markPara1) {
		this.markPara1 = markPara1;
	}

	public String getMarkPara2() {
		return markPara2;
	}

	public void setMarkPara2(String markPara2) {
		this.markPara2 = markPara2;
	}

	public String getMarkPara3() {
		return markPara3;
	}

	public void setMarkPara3(String markPara3) {
		this.markPara3 = markPara3;
	}

	public String getMarkPara4() {
		return markPara4;
	}

	public void setMarkPara4(String markPara4) {
		this.markPara4 = markPara4;
	}

	public String getMarkPara5() {
		return markPara5;
	}

	public void setMarkPara5(String markPara5) {
		this.markPara5 = markPara5;
	}

    
}
