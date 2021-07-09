
window.addEventListener('DOMContentLoaded', event => {

    // Navbar shrink function
    var navbarShrink = function () {
        const navbarCollapsible = document.body.querySelector('#mainNav');
        if (!navbarCollapsible) {
            return;
        }
        if (window.scrollY === 0) {
            navbarCollapsible.classList.remove('navbar-shrink')
        } else {
            navbarCollapsible.classList.add('navbar-shrink')
        }

    };

    // Shrink the navbar 
    navbarShrink();

    // Shrink the navbar when page is scrolled
    document.addEventListener('scroll', navbarShrink);

    // Activate Bootstrap scrollspy on the main nav element
    const mainNav = document.body.querySelector('#mainNav');
    if (mainNav) {
        new bootstrap.ScrollSpy(document.body, {
            target: '#mainNav',
            offset: 74,
        });
    };

    // Collapse responsive navbar when toggler is visible
    const navbarToggler = document.body.querySelector('.navbar-toggler');
    const responsiveNavItems = [].slice.call(
        document.querySelectorAll('#navbarResponsive .nav-link')
    );
    responsiveNavItems.map(function (responsiveNavItem) {
        responsiveNavItem.addEventListener('click', () => {
            if (window.getComputedStyle(navbarToggler).display !== 'none') {
                navbarToggler.click();
            }
        });
    });

});

function submitPost(){
    title = $('#title-input').val()
    content = $('#content-input').val().replace(/n/g, "<br>")

    if (title == ''){
        alert("제목을 입력해주세요.")
        $('#title-input').focus()
        return
    }
    if (content == ''){
        alert("내용을 입력해주세요.")
        $('#content-input').focus()
        return
    }

    data = {'title': title, 'content': content}

    $.ajax({
        type: "POST",
        url: "/api/posts",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response){
            window.location.replace("/")

        }
    })
}

function submitComment(comment){
    let id = getParam("id")
    if (comment == '') {
        alert("댓글을 입력해주세요.")
        return
    } else {
        data = {'commentContent': comment, 'followingCommentId': id}
        $.ajax({
            type: "POST",
            url: `/api/comments`,
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function(response){
                if (response == "success") {
                    getNameAndComments(id)
                } else {
                    alert("로그인이 필요한 기능입니다.")
                    $('#comment-text').val('')
                    window.location.replace("user/login")
                }
            }
        })
    }
}

function getPosts(){
    $('#post-list').empty()
    $.ajax({
        type: "GET",
        url: "/api/posts",
        success: function (response) {
            let count = response.length
            if (count == 0) {
                    addEmptyHtml()
            } else {
                for (let i = 0; i < response.length; i++) {
                    let post = response[i]
                    let title = post['title']
                    let username = post['username']
                    let content = post['content']
                    let modifiedAt = post['createdAt']
                    let views = post['views']
                    let id = post['id']
                    addHtml(title, content, username, modifiedAt, id, views)
                }
            }
        }
    })
}

function getNameAndAll(id){
    $.ajax({
        type: "GET",
        url: `/api/myname`,
        success: function (response) {
            getPost(id,response);
            getComments(id,response);
            return response
        }
    })
}

function getNameAndComments(id){
    $.ajax({
        type: "GET",
        url: `/api/myname`,
        success: function (response) {
            getComments(id, response);
            return response
        }
    })
}


function getPost(id,myname){
    $.ajax({
        type: "GET",
        url: `/api/posts/${id}`,
        success: function (response) {
            let post = response
            let title = post['title']
            let username = post['username']
            let content = post['content'].replace(/(?:\r\n|\r|\n)/g, '<br/>');
            let modifiedAt = post['createdAt']
            let views = post['views']
            if (username != myname){
                let temphtml = `<li>
            <div className = "post" style="min-height: 300px">
                <h3 style = "font-size: 30px; text-align: left; margin-bottom: 0px" id="detail-title">${title}</h3>
                <div style="text-align: left">
                <span style="font-weight: normal; font-size: 17px; font-weight: bold; text-align: right; margin:0 10px 0 5px" id="detail-username">${username}</span>
                <span style="font-weight: normal; font-size: 12px; text-align: right" id="detail-modifiedAt">${modifiedAt}</span>
                <span style="text-align: right; float: right; width: 49%; font-size: 12px; margin-top: 4px">조회수<span>${views}</span></span>
                </div>
                <hr>
            <h5 style="font-weight: normal; font-size: 18px; text-align: left; margin-top: 30px"
                id="detail-content">${content}</h5>
        </div>
        </li>`
                $('#post-list').append(temphtml)
            } else {
                let temphtml = `<li xmlns="http://www.w3.org/1999/html">
            <div className = "post" style="min-height: 300px">
                <h3 style = "font-size: 30px; text-align: left; margin-bottom: 0px" id="detail-title">${title}</h3>
                <div style="text-align: left">
                    <span style="font-weight: normal; font-size: 17px; font-weight: bold; text-align: right; margin:0 10px 0 5px" id="detail-username">${username}</span>
                    <span style="font-weight: normal; font-size: 12px; text-align: right" id="detail-modifiedAt">${modifiedAt}</span>
                    <span style="text-align: right; float: right; width: 49%; font-size: 12px; margin-top: 4px">조회수<span>${views}</span></span>
                </div>
                <div style="text-align: left">
                    <button type="button" class="btn btn-light" onclick="deletePost(${id})" style="font-size: 12px">삭제하기</button>
                </div>    
                <hr>
            <h5 style="font-weight: normal; font-size: 18px; text-align: left; margin-top: 30px"
                id="detail-content">${content}</h5>        
            </div>
        </li>`
                $('#post-list').append(temphtml)
            }
        }
    })
}

function getComments(id,myname){
    $('#comment-list').empty();
    $.ajax({
        type: "GET",
        url: `/api/comments/${id}`,
        success: function (response) {
            for(let i = 0; i < response.length; i++){
                let comment = response[i]
                let commentContent = comment['commentContent']
                let username = comment['username']
                let modifiedAt = comment['modifiedAt']
                let commentId = comment['commentId']
                let followingPostId = comment['followingCommentId']
                if(username==myname){
                    addMyCommentHtml(commentContent, username, modifiedAt,followingPostId, commentId)
                }else {
                    addCommentHtml(commentContent, username, modifiedAt)
                }

            }
        }
    })
}


function deletePost(id) {
    if (confirm("정말 삭제하시겠습니까??") == true) {
        $.ajax({
            type: "DELETE",
            url: `/api/posts/${id}`,
            success: function (response) {
                window.location.replace("/")
            }
        })
    } else {
        return
    }
}



function deleteComment(postId,commentId) {
    if (confirm("댓글을 정말 삭제하시겠습니까?") == true) {
        $.ajax({
            type: "DELETE",
            url: `/api/comments/${commentId}`,
            success: function (response) {
                getNameAndComments(postId)
            }
        })
    } else {
        return
    }
}

function addHtml(title, content, username, modifiedAt, id, views){
    let tempHtml = `<li>
                            <a href="detail.html?id=${id}"  onclick="moveToDetail(${id})">
                            <div class="post" >
                                <div style="font-size: 20px; text-align: left; font-weight: bold; margin-bottom: 6px">${title}</div>
<!--                                <h5 style="font-weight: normal; font-size: 15px; text-align: left">${content}</h5>-->
                                <div style="text-align: left">
                                <span style="font-weight: normal; font-size: 14px; text-align: right; margin-right: 20px;">${username}님</span>
                                <span style="font-weight: normal; font-size: 12px; text-align: right">${modifiedAt}</span>
                                <span style="float: right; text-align:right; font-size: 13px; margin-top: 4px">조회수<span>${views}</span></span>
                                </div>
                                <hr>
                            </div>
                            </a>
                        </li>`
    $('#post-list').append(tempHtml)
}

function addMyCommentHtml(commentContent, username, modifiedAt, postId, commentId){
    let tempHtml = `<li id = "comment-edit-box-my">
                            <div class="comment-box" id="${commentId}-comment-box">
                                <span class="comment-username-box" id="${commentId}-comment-username">${username}</span>
                                <span style="text-align: left; font-size: 11px; font-wight:normal; vertical-align: bottom " id="${commentId}-comment-modifiedAt">${modifiedAt}</span>
                                <span style="font-weight: normal; font-size: 15px;" >
                                <div style="text-align: left; font-wight:normal; margin: 9px 0 0 9px" id="${commentId}-comment-content">${commentContent}</div>
                                <div style="text-align: right">
                                    <button type="button" class="btn btn-light" style="font-size: 12px; padding: 3px; color: #868686" onclick="editMode(${postId},${commentId})" id="${commentId}-comment-edit-btn">수정</button>
                                    <button type="button" class="btn btn-light" style="font-size: 12px; padding: 3px; color: #868686" onclick="deleteComment(${postId},${commentId})" id="${commentId}-comment-delete-btn">삭제</button>     
                                </div>
                                </span>
                            </div>
                        </li>`
    $('#comment-list').append(tempHtml)
    $('#comment-text').val('')
}

function editMode(postId, commentId){
    $(`#${commentId}-comment-username`).hide()
    $(`#${commentId}-comment-content`).hide()
    $(`#${commentId}-comment-modifiedAt`).hide()
    $(`#${commentId}-comment-edit-btn`).hide()
    $(`#${commentId}-comment-delete-btn`).hide()

    commentContent = ''
    $.ajax({
        type: "GET",
        url: `/api/comments/name/${commentId}`,
        success: function (response) {
            let temphtml=`          
                        <div class="input-group mb-3" id="comment-edit-box">
                            <textarea type="text" class="form-control"  aria-label="Recipient's username" aria-describedby="button-addon2" id="comment-edit" rows="3"></textarea>
                            <button class="btn btn-outline-secondary" type="button" id="comment-submit-btn" onClick="editComment(${commentId},${postId})">수정</button>
                        </div>
                        `
            $(`#${commentId}-comment-box`).append(temphtml)
            $('#comment-edit').val(response)
        }
    })
}


function editComment(commentId, postId) {
    content = $('#comment-edit').val()
    data =  {"commentContent": content}

    if (content == ''){
        alert("내용을 입력해주세요.")
    } else {
        $.ajax({
            type: "PUT",
            url: `/api/comments/${commentId}`,
            contentType: "application/json",
            data: JSON.stringify(data),
            success: function (response) {
                getNameAndComments(postId)
            }
        })
    }
}

function addCommentHtml(commentContent, username, modifiedAt){
    let tempHtml = `<li>
                            <div class="comment-box" style="padding-bottom: 10px">
                            <span class="comment-username-box" id="comment-username">${username}</span>
                                <span style="text-align: left; font-size: 11px; font-wight:normal; vertical-align: bottom " id="comment-modifiedAt">${modifiedAt}</span>
                                <span style="font-weight: normal; font-size: 15px;" >
                                <div style="text-align: left; font-wight:normal; margin: 9px 0 0 9px; " id="comment-content">${commentContent}</div>
                                <div style="text-align: right">
                            </span>
                        </div>
                        </li>`
    $('#comment-list').append(tempHtml)
}



function addEmptyHtml(){
    let tempHtml = `<li>
                            <a href="write.html">
                            <div class="post" >
                                <h3 style="font-size: 20px">글을 작성해주세요</h3>
                            </div>
                            </a>
                        </li>`
    $('#post-list').append(tempHtml)
}

function moveToDetail(id){
    window.location.href = "detail.html?id=" + id;
}

function getParam(sname) {
    var params = location.search.substr(location.search.indexOf("?") + 1);
    var sval = "";
    params = params.split("&");
    for (var i = 0; i < params.length; i++) {
        temp = params[i].split("=");
        if ([temp[0]] == sname) { sval = temp[1]; }
    }
    return sval;
}

function signUpPage(){
    $.ajax({
        type: "GET",
        url: "/user/signup",
        success: function (response) {
            alert("완료")
        }
    })
}

function logInPage(){
    $.ajax({
        type: "GET",
        url: "/user/login",
        success: function (response) {
        }
    })
}