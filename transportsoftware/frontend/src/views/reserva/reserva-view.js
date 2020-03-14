import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import '@vaadin/vaadin-form-layout/vaadin-form-layout.js';
import '@vaadin/vaadin-form-layout/vaadin-form-item.js';
import '@vaadin/vaadin-button/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout.js';
import '@vaadin/vaadin-checkbox/src/vaadin-checkbox.js';
import '@vaadin/vaadin-date-picker/src/vaadin-date-picker.js';
import '@vaadin/vaadin-time-picker/src/vaadin-time-picker.js';
import '@vaadin/vaadin-text-field/vaadin-text-field.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-item.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class ReservaView extends PolymerElement {
  static get template() {
    return html`
<custom-style>
 <style include="shared-styles lumo-typography">
          #reserva-view {
              display: block;
              padding: 1rem;
          }

          :host {
              display: block;
              padding: 1rem;
          }

          .button-layout {
              display: flex;
              flex-wrap: wrap-reverse;
              width: 100%;
              justify-content: flex-end
          }
        </style>
</custom-style>
<vaadin-vertical-layout id="wrapper" theme="padding">
 <h1>Reservar un muelle</h1>
 <vaadin-form-layout style="align-self: center;">
  <vaadin-form-item style="width: 100%;">
   <label slot="label">¿Qué desea hacer?</label>
   <vaadin-checkbox id="check_reservar">
     Reservar muelle 
   </vaadin-checkbox>
   <vaadin-checkbox id="check_modificar">
     Modificar reserva 
   </vaadin-checkbox>
  </vaadin-form-item>
  <br>
  <vaadin-form-item style="width: 450px;">
   <label slot="label">Pedido</label>
   <vaadin-text-field id="pedido" style="width: 300px;"></vaadin-text-field>
  </vaadin-form-item>
  <vaadin-form-item style="width: 420px;">
   <label slot="label">Matrícula</label>
   <vaadin-text-field id="matricula" class="full-width" value="" style="width: 300px;"></vaadin-text-field>
  </vaadin-form-item>
  <br>
  <vaadin-form-item colspan="2">
   <label slot="label">Acción</label>
   <vaadin-checkbox id="check_descarga">
     Descarga 
   </vaadin-checkbox>
   <vaadin-checkbox id="check_carga">
     Carga 
   </vaadin-checkbox>
  </vaadin-form-item>
  <vaadin-form-item colspan="2">
   <label slot="label">Tipo de camión</label>
   <vaadin-checkbox id="check_trailer">
     Trailer 
   </vaadin-checkbox>
   <vaadin-checkbox id="check_furgoneta">
     Furgoneta 
   </vaadin-checkbox>
   <vaadin-checkbox id="check_lona">
     Lona 
   </vaadin-checkbox>
  </vaadin-form-item>
  <vaadin-form-item style="width: 450px;">
   <label slot="label">Fecha de reserva</label>
   <vaadin-date-picker style="flex-grow: 0; width: 300px;" id="seleccion_dia"></vaadin-date-picker>
  </vaadin-form-item>
  <vaadin-form-item>
   <label slot="label">Hora de reserva</label>
   <vaadin-time-picker id="seleccion_hora" style="width: 300px;" max="14:00:00.00" min="06:00:00.00" value="06:00"></vaadin-time-picker>
  </vaadin-form-item>
 </vaadin-form-layout>
 <vaadin-horizontal-layout style="display:flex; flex-wrap:wrap-reverse; width:100%; justify-content: flex-end;">
  <vaadin-button theme="primary error" id="cancelar_reserva">
    Cancelar reserva 
  </vaadin-button>
  <vaadin-button theme="tertiary" id="cancelar" slot="">
    Vaciar selección 
  </vaadin-button>
  <vaadin-button theme="primary" id="reservar">
    Save 
  </vaadin-button>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
  }

  static get is() {
    return 'reserva-view';
  }

  static get properties() {
    return {
      // Declare your properties here.
    };
  }
}

customElements.define(ReservaView.is, ReservaView);
