<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-floating">
  <select class="form-select h5 text" id="<c:out value="${param.id}"/>" name="<c:out value="${param.id}"/>">
    <option value="1">Agronomía</option>
    <option value="2">Almagro</option>
    <option value="3">Balvanera</option>
    <option value="4">Barracas</option>
    <option value="5">Belgrano</option>
    <option value="6">Boedo</option>
    <option value="7">Caballito</option>
    <option value="8">Chacarita</option>
    <option value="9">Coghlan</option>
    <option value="10">Colegiales</option>
    <option value="11">Constitución</option>
    <option value="12">Flores</option>
    <option value="13">Floresta</option>
    <option value="14">La Boca</option>
    <option value="15">La Paternal</option>
    <option value="16">Liniers</option>
    <option value="17">Mataderos</option>
    <option value="18">Monte Castro</option>
    <option value="19">Montserrat</option>
    <option value="20">Nueva Pompeya</option>
    <option value="21">Núñez</option>
    <option value="22">Palermo</option>
    <option value="23">Parque Avellaneda</option>
    <option value="24">Parque Chacabuco</option>
    <option value="25">Parque Chas</option>
    <option value="26">Parque Patricios</option>
    <option value="27">Puerto Madero</option>
    <option value="28">Recoleta</option>
    <option value="29">Retiro</option>
    <option value="30">Saavedra</option>
    <option value="31">San Cristóbal</option>
    <option value="32">San Nicolás</option>
    <option value="33">San Telmo</option>
    <option value="34">Vélez Sársfield</option>
    <option value="35">Versalles</option>
    <option value="36">Villa Crespo</option>
    <option value="37">Villa del Parque</option>
    <option value="38">Villa Devoto</option>
    <option value="39">Villa General Mitre</option>
    <option value="40">Villa Lugano</option>
    <option value="41">Villa Luro</option>
    <option value="42">Villa Ortúzar</option>
    <option value="43">Villa Pueyrredón</option>
    <option value="44">Villa Real</option>
    <option value="45">Villa Riachuelo</option>
    <option value="46">Villa Santa Rita</option>
    <option value="47">Villa Soldati</option>
    <option value="48">Villa Urquiza</option>
  </select>
  <label for="<c:out value="${param.id}"/>" class="placeholder-text h5">Barrio</label>
</div>
