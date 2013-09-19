<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>  
        <script type="text/javascript" src="js/jquery-2.0.2.js"></script>
    	<script type="text/javascript" src="js/jquery.min.js"></script>
    	<script type="text/javascript" src="js/padrao.js"></script>
        <title>Controle de Sala</title>
        <link href="css/comum.css" media="all" rel="stylesheet" type="text/css" />
        <link href="css/login.css" media="all" rel="stylesheet" type="text/css" />
    </head>
    <body>
      <section id="geral">
        <header id="topo_pagina">
            <div class="navbar navbar-inverse affix">
              <div class="navbar-inner2">
              <div class="container">
                <a class="brand" href="index.jsp">Controle de Sala</a>
                <div class="nav-collapse collapse">
                <ul class="nav">
                </ul>
                </div>
              </div>
              </div>
            </div>
            <div class="vAux"></div>
        </header>
        <div id="conteudo">
          <div id="login-form">
      		<h1>Login</h1>
      		 <fieldset>
         		 <form action="login" method="post">                
                           
                  	 <input name="username" id="user"  type="text" value="" placeholder="Usu&aacute;rio"
                  	 value="Usuário" onBlur="if(this.value=='')this.value='Usuário'" onFocus="if(this.value=='Email')this.value='' "/>
              
                	 <input name="senha" id="pass" type="password" value="" placeholder="Senha"
                	 value="Senha" onBlur="if(this.value=='')this.value='Senha'" onFocus="if(this.value=='Password')this.value='' "/>               
                
                  	 <input id="btn-login" type="submit" value="Logar"/>              
          		 </form>
          	</fieldset>
            </div>
          </div>
        </section>
    </body>
</html>