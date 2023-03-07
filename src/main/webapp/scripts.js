function authUser(){
		let login = $("#login").val();
		let password = $("#password").val();
		let str = "action=auth&login="+login+"&password="+password;
		$.ajax({
			type: "POST",
			url: "User",
			data: str,
			success: function(answer){
				window.location.href = "?page=catalog";
			}
		})
	}
	
function regNewUser(){
		let login = $("#login").val();
		let password = $("#password").val();
		let email = $("#email").val();
		let number = $("#number").val();
		let str = "action=reg&login="+login+"&password="+password+"&email="+email+"&number="+number;
		$.ajax({
			type: "POST",
			url: "User",
			data: str,
			success: function(answer){
				window.location.href = "?page=auth";
			}
		})
	}
	
function logoutUser(){
		let str = "action=logout";
		$.ajax({
			type: "POST",
			url: "User",
			data: str,
			success: function(answer){
				location.reload();
			}
		})
	}
	
function editOrder(id) {
			let str = "action=delete&id="+id;
			$.ajax({
				type: "POST",
				url: "Cart",
				data: str,
				success: function(answer){
					location.reload();
				}
			})
		}
		
function confirmOrder() {
			let str = "action=confirm";
			$.ajax({
				type: "POST",
				url: "Cart",
				data: str,
				success: function(answer){
					window.location.href = "?page=order";
				}
			})
		}
		
function add(id) {
			let str =  "id=" + id;
			$.ajax({
				  type: "POST",
				  url: "Catalog",
				  data: str,
				  success: function(answer){
					location.reload();
				  }
			});
		}
		
function changeStatus(id) {
			let str =  "id=" + id;
			$.ajax({
				  type: "POST",
				  url: "Order",
				  data: str,
				  success: function(answer){
				   location.reload();
				  }
			});
		}
		
function deleteGoodDB(id) {
			let str = "action=deleteGoodDB&id="+id;
			$.ajax({
				type: "POST",
				url: "User",
				data: str,
				success: function(answer){
					window.location.href = "?page=catalog";
				}
			})
		}
		
function addGoodDB() {
			let category = $('#category option:selected').val();
			let popularity = $('#popularity option:selected').val();		
			let title = $('#title').val();
			let price = $('#price').val();
			let info = $('#info').val();
			let img = $('#img').val();		
			let str = "action=addGoodDB&category="+category+"&popularity="+popularity+"&title="+title+"&price="+price+"&info="+info+"&img="+img;
			$.ajax({
				type: "POST",
				url: "User",
				data: str,
				success: function(answer){
					window.location.href = "?page=catalog";
				}
			})
		}
		
function editGoodDB(id) {
			let category = $('#category option:selected').val();
			let popularity = $('#popularity option:selected').val();		
			let title = $('#title').val();
			let price = $('#price').val();
			let info = $('#info').val();
			let img = $('#img').val();		
			let str = "action=editGoodDB&id="+id+"&category="+category+"&popularity="+popularity+"&title="+title+"&price="+price+"&info="+info+"&img="+img;
			$.ajax({
				type: "POST",
				url: "User",
				data: str,
				success: function(answer){
					location.reload();
				}
			})
		}
		
function vision(){
            display == "block" ? display = "none" : display = "block";
            document.querySelector('#promoFirst').style.display = display;
        }