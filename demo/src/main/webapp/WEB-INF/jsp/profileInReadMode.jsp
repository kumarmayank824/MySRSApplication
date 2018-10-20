<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-sm-9">
	 <div class="tab-content">
		  <div class="tab-pane active">
	            <c:if test="${user.signInType == 'Student'}">
	               <div style="padding-bottom:10px;">
	               <div class="col-xs-12">
	                    <button type="button" ng-click="loadProfileInEditMode()" style="background-color:#f44336;color:white;margin-right:10px;" class="btn btn-default "><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</button>
	                  	<button type="button" onclick="window.history.go(-1); return false;" style="background-color:grey;color:white "class="btn btn-default ">Cancel</button>
	                </div>
	              </div>
	              <br><br>
	          </c:if>
	          <div>
	              <div class="col-xs-6">
	                  <label for="username"><h4 class="profileLabelColor">Name</h4></label>
	                  <p class="camelCase">
	                     ${user.username}
	                  </p>
	              </div>
	          </div>
	          <div>
	              <div class="col-xs-6">
	                <label for="email"><h4 class="profileLabelColor">Email</h4></label>
	                  <p>
	                     ${user.email}
	                  </p>
	              </div>
	          </div>
	
	          <div>
	              <div class="col-xs-6">
	                  <label for="signInType"><h4 class="profileLabelColor">Role</h4></label>
	                  <p>
	                     ${user.signInType}
	                  </p>
	              </div>
	          </div>
	
	          <div>
	              <div class="col-xs-6">
	                 <label for="semester"><h4 class="profileLabelColor">Semester</h4></label>
	                  <c:if test="${user.signInType == 'Student'}">
	                     <p>${user.semester}</p>
	                  </c:if>
	                  <c:if test="${user.signInType == 'Teacher'}">
	                     <p>N/A</p>
	                  </c:if>
	              </div>
	          </div>
	          <div>
	              <div class="col-xs-6">
	                  <label for="batch"><h4 class="profileLabelColor">Batch</h4></label>
	                  <c:if test="${user.signInType == 'Student'}">
	                     <p>${user.batch}</p>
	                  </c:if>
	                  <c:if test="${user.signInType == 'Teacher'}">
	                     <p>N/A</p>
	                  </c:if>
	              </div>
	          </div>
	          <div>
	              <div class="col-xs-6">
	                  <label for="course"><h4 class="profileLabelColor">Course</h4></label>
	                  <c:if test="${user.signInType == 'Student'}">
	                     <p>${user.course}</p>
	                  </c:if>
	                  <c:if test="${user.signInType == 'Teacher'}">
	                     <p>N/A</p>
	                  </c:if>
	              </div>
	          </div>
	          <div>
	              <div class="col-xs-6">
	                  <label for="password"><h4 class="profileLabelColor">Password</h4></label>
	                  <p>
	                     ${user.password}
	                  </p>
	                  <p>
	                    <b><h6 class="profileLabelOnlyColor"><i>Hint : </i>${passwordHint}</h6></b>
	                  </p>
	              </div>
	          </div>
	          <div>
	              <div class="col-xs-6">
	                  <c:if test="${user.signInType == 'Student'}">
	                     <label for="noOfTotalUpload"><h4 class="profileLabelColor">Total Uploads</h4></label>
	                     <p>${noOfTotalUpload}</p>
	                  </c:if>
	                  <c:if test="${user.signInType == 'Teacher'}">
	                     <label for="noOfTotalUpload"><h4 class="profileLabelColor">Total Documents Marked</h4></label>
	                     <p>${noOfAttachmentMarked}</p>
	                  </c:if>
	              </div>
	          </div>
	          <c:if test="${user.signInType == 'Student'}">
	             <div class="form-group">
	               <div class="col-xs-12">
	                    <br>
	                    <button type="button" ng-click="loadProfileInEditMode()" style="background-color:#f44336;color:white;margin-right:10px;" class="btn btn-default "><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit</button>
	                  	<button type="button" onclick="window.history.go(-1); return false;" style="background-color:grey;color:white "class="btn btn-default ">Cancel</button>
	                </div>
	              </div>
	          </c:if>
		</div><!--/tab-pane-->
	 </div><!--/tab-content-->
</div><!--/col-9-->