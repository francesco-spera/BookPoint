/**
 * 
 */

$(document).ready(function(){

	$(".modificaTipo").click(function(){
		$(".formModificaTipo").slideToggle();

	});


	$("#search-button").click(function(){
		$("#form-ricerca-libri").submit();
	});

	$(".add-libro").click(function(){
		$(".container-inserimento").slideToggle();
		$("#libro-non-trovato-display").hide();
		$(".libro-trovato-display").hide();
	});

	$("#bottone-inserisci-autori").click(function(){
		$(".aggiungi-autori").slideToggle();
	});

	$(".bottone-aggiungi-autore").click(function(){
		$(".aggiungi-autori").append("<div class=\"aggiungi-autore\">Nome: <input type=\"text\" name=\"autore\"> </div>");
	});

	$(".visualizza").click(function(){
		var isbn= $(this).attr("name");
		$.post("visualizzaLibro",{"isbn":isbn}, function(data, status){
			window.location.href= "AmministratoreVisualizzaLibro.jsp";
		});

	});

	$(".modificaButton").click(function(){
		var id= this.id;
		var tipo= $("#"+id+"Tipo").val();
		var nuovoAttributo= $("#"+id+"NuovoAttributo").val();

		$.post("modificaAttributo", {"tipo":tipo, "nuovoAttributo":nuovoAttributo},function(data, statuts){
			if(data.result){
				$("#"+id+"Libro").text(data.nuovoAttributo);
			}
		});
	});
	
	$(".bottone-elimina").click(function(){
		alert("aaa");
		var id= this.id;
		var idRecensione= $("#recensione"+id).val(); 
		$.post("eliminaRecensione", {"idRecensione": idRecensione}, function(data){
			$("#div-recensione"+id).remove();
		});
	});

});
