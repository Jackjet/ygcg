    //������JavaScript 
	   var progress;  
       var uploadProcessTimer = null;  
       function formSubmit(){    
           uploadProcessTimer =window.setInterval("getFileUploadProcess()",100);//ÿ��100����ִ��callback  
           document.forms[0].submit();  
        }    
        function getFileUploadProcess() {    
              $.ajax({  
                     type:"GET",  
                     url:"fileUploadStatusServlet",  
                     dataType:"text",  
                     cache:false,  
                     success:function(data){  
                              
                               
                         if(data=="100%"){  
                             window.clearInterval(uploadProcessTimer);  
                         }else{  
                              progress=data;  
                             $("#show").width(data);  
                             $("#msg").text(data);  
                         }  
                     }  
             });  
         };  