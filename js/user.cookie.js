<script>
  window.onload = function(){
    var oForm = document.getElementById('loginForm');
    var oUser = document.getElementById('exampleInputName2');
    var oPswd = document.getElementById('inputPassword3');
    var oRemember = document.getElementById('checkbox3');
    //ҳ���ʼ��ʱ������ʺ�����cookie���������
    if(getCookie('exampleInputName2') && getCookie('inputPassword3')){
      oUser.value = getCookie('exampleInputName2');
      oPswd.value = getCookie('inputPassword3');
      oRemember.checked = true;
    }
    //��ѡ��ѡ״̬�����ı�ʱ�����δ��ѡ�����cookie
    oRemember.onchange = function(){
      if(!this.checked){
        delCookie('exampleInputName2');
        delCookie('inputPassword3');
      }
    };
    //���ύ�¼�����ʱ�������ѡ���ǹ�ѡ״̬�򱣴�cookie
    oForm.onsubmit = function(){
      if(remember.checked){ 
        setCookie('exampleInputName2',oUser.value,7); //�����ʺŵ�cookie����Ч��7��
        setCookie('inputPassword3',oPswd.value,7); //�������뵽cookie����Ч��7��
      }
    };
  };
  //����cookie
  function setCookie(name,value,day){
    var date = new Date();
    date.setDate(date.getDate() + day);
    document.cookie = name + '=' + value + ';expires='+ date;
  };
  //��ȡcookie
  function getCookie(name){
    var reg = RegExp(name+'=([^;]+)');
    var arr = document.cookie.match(reg);
    if(arr){
      return arr[1];
    }else{
      return '';
    }
  };
  //ɾ��cookie
  function delCookie(name){
    setCookie(name,null,-1);
  };
</script>