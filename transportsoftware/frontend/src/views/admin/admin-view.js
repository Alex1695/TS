import { PolymerElement } from "@polymer/polymer/polymer-element.js";
import { html } from "@polymer/polymer/lib/utils/html-tag.js";
import "@vaadin/vaadin-split-layout/vaadin-split-layout.js";
import "@vaadin/vaadin-grid/vaadin-grid.js";
import "@vaadin/vaadin-grid/vaadin-grid-column.js";
import "@vaadin/vaadin-form-layout/vaadin-form-layout.js";
import "@vaadin/vaadin-form-layout/vaadin-form-item.js";
import "@vaadin/vaadin-text-field/vaadin-text-field.js";
import "@vaadin/vaadin-text-field/vaadin-password-field.js";
import "@vaadin/vaadin-button/vaadin-button.js";
import "@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout.js";
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-upload/src/vaadin-upload-file.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';

class AdminView extends PolymerElement {
  static get template() {
    return html`
<style include="shared-styles">
        :host {
          display: block;
          height: 100%;
        }
      </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;" id="vaadinVerticalLayout">
 <h1 id="h1">Panel de administración</h1>
 <vaadin-horizontal-layout theme="margin" style="width: 100%; height: auto;">
  <vaadin-vertical-layout theme="margin" id="verticalLayoutUploadDocks" style="width: 50%;">
   <h4>Carga de Muelles</h4>
  </vaadin-vertical-layout>
  <vaadin-vertical-layout theme="margin" id="verticalLayoutUploadOrders" style="width: 50%;">
   <h4>Carga de pedidos</h4>
  </vaadin-vertical-layout>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
  }


  static get is() {
    return "admin-view";
  }

  static get properties() {
    return {
      // Declare your properties here.
    };
  }
}

customElements.define(AdminView.is, AdminView);
