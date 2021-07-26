/**
 * Validação de formulário
 * @author Luis Starlino 
 */

function validar(){
	let nome = frmContato.nome.value
	let telefone = frmContato.telefone.value
	if(nome === ""){
		alert("Digite um nome")
		frmContato.nome.focus()
		return false
	} else if(telefone ===""){
		alert("Digite um telefone")
		frmContato.telefone.focus()
		return false		
	}else {
		document.forms['frmContato'].submit();
	}
}