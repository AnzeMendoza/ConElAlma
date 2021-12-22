/*funcion que se encarga de mostrar y ocultar formulario*/
function showAndHide() {
  let x = document.getElementById("showHide");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
    location.href = "http://localhost:8080/bebida/todos";
  }
}
