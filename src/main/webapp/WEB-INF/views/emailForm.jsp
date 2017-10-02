<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    

<c:if test="${userEmail eq mull}">
    <c:set var="userEmail" value="tertertert"/>
</c:if> 
<div class="row justify-content-center">
        <div class="card col-10 col-md-8 cardBody">
            <div class="card-block">
                <c:if test="${emailMsg eq null}">
                    <form name="emailForm" action="/sendmail" method="POST" id='emailForm'>
                        <input type="hidden" value="${param.dataType}" name="dataType"/>
                        <input type="hidden" value="${message}" name="prevMessage"/>
                        <c:if test="${emailErr eq null}">
                            <div class="form-group">
                                <div class="input-group justify-content-center">
                                    <input class="form-control col-md-8" type="email" id="email" name="email" 
                                           value="${userEmail}" title="Send Me by eMail" placeholder="Insert eMail">
                                    <button type="submit" class="btn btn-default input-group-addon" data-toggle="tooltip" data-placement="top" title="Send Me by eMail">></button>
                                 </div>
                            </div>
                        </c:if>
                        
                        <c:if test="${emailErr ne null}">
                            <div class="form-group has-danger justify-content-center">
                                <div class="input-group justify-content-center">
                                    <input class="form-control form-control-danger col-md-8" type="email" id="email" name="email" 
                                           value="${userEmail}" title="Send Me by eMail" placeholder="Insert eMail">
                                     <button type="submit" class="btn btn-default input-group-addon" data-toggle="tooltip" data-placement="top" title="Send Me by eMail">></button>
                                 </div>
                                 <div class="form-control-feedback text-center" ><small>${emailErr}</small></div>
                            </div>
                        </c:if>
                            
                    </form>
                </c:if>
               
                <c:if test="${emailMsg ne null}">
                    <div class="alert alert-success text-center" role="alert">
                       <strong>${emailMsg}</strong>
                     </div>
                </c:if>
            </div>
        </div>
     </div>
