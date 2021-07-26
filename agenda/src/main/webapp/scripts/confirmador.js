/**
 * Confirmador de Exlusão de um contato
 * @author Luis Starlino 
 */

function confirmar(id){
	let resp = confirm("Deseja excluir mesmo esse contato?")
	if (resp) {
		//alert(id)
		window.location.href = "delete?id=" + id
	}
}