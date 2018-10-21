<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-sm-9">
	 <div class="tab-content">
		  <div class="tab-pane active">
		        <form:form action="/updateProfileDetails" method="post" modelAttribute="user">
		            <c:if test="${user.signInType == 'Student'}">
		               <div style="padding-bottom:10px;">
		               <div class="col-xs-12">
		                    <button type="submit" style="background-color:#f44336;color:white;margin-right:10px;" class="btn btn-default "> Update</button>
		                  	<button type="button" ng-click="backToReadMode()" style="background-color:grey;color:white "class="btn btn-default ">Cancel</button>
		                </div>
		              </div>
		              <br><br>
		          </c:if>
		          
		          <div>
		              <div class="col-xs-5">
		                  <label for="username"><h4 class="profileLabelColor">Name</h4></label>
		                  <input type="text" class="form-control" value="${user.username}"  name="username" id="username" placeholder="change your name" title="enter your name." required/>
		              </div>
		          </div>
		          
		          <div>
		              <div class="col-xs-5">
		                  <label for="course"><h4 class="profileLabelColor">Course</h4></label>
		                  <c:if test="${user.signInType == 'Student'}">
		                     <select class="form-control" name="course">
							      <option value="M.Tech (IT)" <c:if test="${user.course == 'M.Tech (IT)'}"> selected </c:if>> M.Tech (IT)</option>
							      <option value="M.Tech (VLSI)" <c:if test="${user.course == 'M.Tech (VLSI)'}"> selected </c:if>> M.Tech (VLSI)</option>
								  <option value="M.Tech (EDS)" <c:if test="${user.course == 'M.Tech (EDS)'}"> selected </c:if>> M.Tech (EDS)</option>
							  </select>
		                  </c:if>
		                  <c:if test="${user.signInType == 'Teacher'}">
		                     <p>N/A</p>
		                  </c:if>
		              </div>
		          </div>
		          
		          <div>
		              <div class="col-xs-5">
		                 <label for="semester"><h4 class="profileLabelColor">Semester</h4></label>
		                  <c:if test="${user.signInType == 'Student'}">
		                      <select class="form-control" name="semester">
							      <option value="V" <c:if test="${user.semester == 'V'}"> selected </c:if>> V</option>
								  <option value="VI" <c:if test="${user.semester == 'VI'}"> selected </c:if>> VI</option>
								  <option value="VII" <c:if test="${user.semester == 'VII'}"> selected </c:if>> VII</option>
							  </select>
		                  </c:if>
		                  <c:if test="${user.signInType == 'Teacher'}">
		                     <p>N/A</p>
		                  </c:if>
		              </div>
		          </div>
		          
		          <div>
		              <div class="col-xs-5">
		                  <label for="batch"><h4 class="profileLabelColor">Batch</h4></label>
		                  <c:if test="${user.signInType == 'Student'}">
		                     <select class="form-control" name="batch">
							      <option value="Batch 1" <c:if test="${user.batch == 'Batch 1'}"> selected </c:if>> Batch 1</option>
				 				  <option value="Batch 2" <c:if test="${user.batch == 'Batch 2'}"> selected </c:if>> Batch 2</option>
								  <option value="Batch 3" <c:if test="${user.batch == 'Batch 3'}"> selected </c:if>> Batch 3</option>
								  <option value="Batch 4" <c:if test="${user.batch == 'Batch 4'}"> selected </c:if>> Batch 4</option>
								  <option value="Batch 5" <c:if test="${user.batch == 'Batch 5'}"> selected </c:if>> Batch 5</option>
								  <option value="Batch 6" <c:if test="${user.batch == 'Batch 6'}"> selected </c:if>> Batch 6</option>
							 	  <option value="Batch 7" <c:if test="${user.batch == 'Batch 7'}"> selected </c:if>> Batch 7</option>
								  <option value="Batch 8" <c:if test="${user.batch == 'Batch 8'}"> selected </c:if>> Batch 8</option>
								  <option value="Batch 9" <c:if test="${user.batch == 'Batch 9'}"> selected </c:if>> Batch 9</option>
								  <option value="Batch 10" <c:if test="${user.batch == 'Batch 10'}"> selected </c:if>> Batch 10</option>
							  </select>
		                  </c:if>
		                  <c:if test="${user.signInType == 'Teacher'}">
		                     <p>N/A</p>
		                  </c:if>
		              </div>
		          </div>
		          
		          <div>
		              <div class="col-xs-5">
		                <label for="email"><h4 class="profileLabelColor">Email</h4></label>
		                  <p>
		                     ${user.email}
		                  </p>
		                  <%-- <input type="hidden" class="form-control" name="email" value="${user.email}"> --%>
		              </div>
		          </div>
		
		          <div>
		              <div class="col-xs-5">
		                  <label for="signInType"><h4 class="profileLabelColor">Role</h4></label>
		                  <p>
		                     ${user.signInType}
		                  </p>
		                  <%-- <input type="hidden" class="form-control" name="signInType" value="${user.signInType}"> --%>
		              </div>
		          </div>
		
		          
		          <div>
		              <div class="col-xs-5">
		                  <label for="password"><h4 class="profileLabelColor">Password</h4></label>
		                  <p>
		                     ${user.password}
		                  </p>
		                  <p>
		                    <b><h6 class="profileLabelOnlyColor"><i>Hint : </i>${passwordHint}</h6></b>
		                  </p>
		                  <%-- <input type="hidden" class="form-control" name="password" value="${user.password}"> --%>
		              </div>
		          </div>
		          
		          <div>
		              <div class="col-xs-5">
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
		                    <button type="submit" style="background-color:#f44336;color:white;margin-right:10px;" class="btn btn-default "> Update</button>
		                  	<button type="button" ng-click="backToReadMode()" style="background-color:grey;color:white "class="btn btn-default ">Cancel</button>
		                </div>
		              </div>
		          </c:if>
		          
		  	</form:form>
		</div><!--/tab-pane-->
	 </div><!--/tab-content-->
</div><!--/col-9-->