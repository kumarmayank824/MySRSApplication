package com.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="secret_code")
@Component
public class SecretCode implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Column(name="secretCode")
		private String secretCode;
        
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSecretCode() {
			return secretCode;
		}

		public void setSecretCode(String secretCode) {
			this.secretCode = secretCode;
		}
		
}
