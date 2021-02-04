package com.ohalo.user.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ohalo.user.utils.LocalDateDeserializer;
import com.ohalo.user.utils.LocalDateSerializer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties({ "id" })
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto implements Serializable {

	private static final long serialVersionUID = -8159484217271334987L;

	private Long id;

	private String username;

	private String password;

	@NotNull(message = "Firstname is mandatory")
	private String firstName;

	@NotNull(message = "Lastname is mandatory")
	private String lastName;

	private Long userId;

	private Long orgUnitId;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate startDate;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonInclude(value = Include.NON_NULL)
	private LocalDate endDate;

	private String socSecNumber;
}
