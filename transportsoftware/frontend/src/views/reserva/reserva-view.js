import { PolymerElement } from '@polymer/polymer/polymer-element.js';
import { html } from '@polymer/polymer/lib/utils/html-tag.js';
import '@vaadin/vaadin-form-layout/vaadin-form-layout.js';
import '@vaadin/vaadin-form-layout/vaadin-form-item.js';
import '@vaadin/vaadin-button/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/vaadin-vertical-layout.js';
import '@vaadin/vaadin-text-field/vaadin-text-field.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-item.js';
import '@vaadin/vaadin-checkbox/src/vaadin-checkbox.js';
import '@vaadin/vaadin-combo-box/src/vaadin-combo-box.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';

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
 <vaadin-horizontal-layout theme="spacing" style="align-self: flex-end;">
  <label>Seleccione un idioma: </label>
  <vaadin-checkbox id="check_spanish">
   Español
  </vaadin-checkbox>
  <vaadin-checkbox id="check_english">
   Inglés
  </vaadin-checkbox>
 </vaadin-horizontal-layout>
 <h1>Reservar un muelle</h1>
 <vaadin-form-layout style="align-self: center;">
  <vaadin-form-item style="width: 100%;">
   <label slot="label">¿Qué desea hacer?</label>
   <vaadin-checkbox id="check_book">
     Reservar muelle 
   </vaadin-checkbox>
   <vaadin-checkbox id="check_modify">
     Modificar reserva 
   </vaadin-checkbox>
  </vaadin-form-item>
  <br>
  <vaadin-form-item style="width: 450px;">
   <label slot="label">Pedido</label>
   <vaadin-text-field id="order" style="width: 300px;"></vaadin-text-field>
   <vaadin-button theme="icon" aria-label="Add new" id="check">
    <iron-icon icon="lumo:search"></iron-icon>
   </vaadin-button>
  </vaadin-form-item>
  <vaadin-form-item style="width: 420px;" id="item_plate">
   <label slot="label">Matrícula</label>
   <vaadin-text-field id="plate" class="full-width" value="" style="width: 300px;"></vaadin-text-field>
  </vaadin-form-item>
  <br>
  <vaadin-form-item colspan="2" id="item_action">
   <label slot="label">Acción</label>
   <vaadin-combo-box id="combo_action" style="width: 300px;" page-size="5"></vaadin-combo-box>
  </vaadin-form-item>
  <vaadin-form-item colspan="2" id="item_type">
   <label slot="label">Tipo de camión</label>
   <vaadin-combo-box id="combo_type" style="width: 300px;"></vaadin-combo-box>
  </vaadin-form-item>
  <vaadin-form-item style="width: 450px;" id="item_date">
   <label slot="label">Fecha de reserva</label>
   <vaadin-combo-box id="date_selection" style="width: 300px;"></vaadin-combo-box>
  </vaadin-form-item>
  <vaadin-form-item id="item_hour">
   <label slot="label">Hora de reserva</label>
   <vaadin-combo-box id="hour_selection" style="width: 300px;"></vaadin-combo-box>
   <vaadin-button theme="icon" aria-label="Add new" id="check_hours">
    <iron-icon icon="lumo:search"></iron-icon>
   </vaadin-button>
   <vaadin-button theme="icon" aria-label="Add new" id="check_hours_modify">
    <iron-icon icon="lumo:edit"></iron-icon>
   </vaadin-button>
  </vaadin-form-item>
 </vaadin-form-layout>
 <vaadin-horizontal-layout style="display:flex; flex-wrap:wrap-reverse; width:100%; justify-content: flex-end;">
  <vaadin-button theme="primary error" id="cancel_booking">
    Cancelar reserva 
  </vaadin-button>
  <vaadin-button theme="tertiary" id="cancel" slot="">
    Vaciar selección 
  </vaadin-button>
  <vaadin-button theme="primary" id="check_info">
    Verificar información 
  </vaadin-button>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout style="width: 100%; justify-content: flex-end;" theme="spacing">
  <label style="align-self: center;">Si deseas acceder como administrador, pulse en el botón:</label>
  <vaadin-button theme="icon" aria-label="Add new" id="admin_button">
   <iron-icon icon="lumo:user"></iron-icon>
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
