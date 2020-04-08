import {PolymerElement} from '@polymer/polymer/polymer-element.js';
import {html} from '@polymer/polymer/lib/utils/html-tag.js';
import '@vaadin/vaadin-board/vaadin-board.js';
import '@vaadin/vaadin-charts/vaadin-chart.js';
import '@vaadin/vaadin-lumo-styles/all-imports.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-grid/src/vaadin-grid.js';
import '@vaadin/vaadin-combo-box/src/vaadin-combo-box.js';
import '@polymer/iron-icon/iron-icon.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';

class TFTView extends PolymerElement {
  static get template() {
    return html`
<style include="shared-styles lumo-badge lumo-typography">
        :host {
          background-color: var(--lumo-contrast-10pct);
          box-sizing: border-box;
          display: block;
          font-size: var(--lumo-font-size-m);
          height: 100%;
          overflow: auto;
          padding: var(--lumo-space-m) var(--lumo-space-l);
        }

        vaadin-board {
          margin: 0 auto;
          max-width: 1024px;
        }

        / Wrapper /
        .wrapper {
          display: flex;
          padding: var(--lumo-space-s);
        }

        / Card /
        .card {
          align-items: baseline;
          background-color: var(--lumo-base-color);
          border-radius: var(--lumo-border-radius);
          box-shadow: var(--lumo-box-shadow-xs);
          display: flex;
          flex-direction: column;
          overflow: hidden;
          width: 100%;
        }

        .card h2 {
          margin-bottom: 0;
          margin-top: var(--lumo-space-m);
        }

        .card h3 {
          margin-bottom: var(--lumo-space-xs);
          margin-left: var(--lumo-space-m);
          margin-top: var(--lumo-space-m);
        }

        / Spacing /
        .space-m {
          padding: var(--lumo-space-m);
        }

        / Text colors /
        .error-text {
          color: var(--lumo-error-text-color);
        }

        .primary-text {
          color: var(--lumo-primary-text-color);
        }

        .secondary-text {
          color: var(--lumo-secondary-text-color);
        }

        .success-text {
          color: var(--lumo-success-text-color);
        }

        / Charts /
        vaadin-chart {
          padding-top: var(--lumo-space-s);
        }

        / Grid /
        vaadin-grid {
          height: 300px;
        }
      </style>
<vaadin-vertical-layout style="width: 100%; height: 100%;">
 <vaadin-horizontal-layout theme="spacing" style="align-self: center;">
  <vaadin-horizontal-layout theme="spacing" id="entrada">
   <vaadin-text-field label="Introduce una matricula" placeholder="" id="text_entrance" style="align-self: center;"></vaadin-text-field>
   <vaadin-combo-box id="combo_hours" style="align-self: flex-end;" label="Selecciona la hora de reserva"></vaadin-combo-box>
   <vaadin-button theme="icon" aria-label="Add new" id="search_button" style="align-self: flex-end;">
    <iron-icon icon="lumo:search"></iron-icon>
   </vaadin-button>
   <vaadin-text-field label="Introduce la hora de entrada" placeholder="Formato XX:XX" id="text_hour_entrance"></vaadin-text-field>
   <vaadin-button theme="primary" id="button_entrance" style="align-self: flex-end;">
     Entrar 
   </vaadin-button>
  </vaadin-horizontal-layout>
  <vaadin-horizontal-layout theme="spacing" id="salida">
   <vaadin-text-field label="Introduce una matricula" id="text_exit"></vaadin-text-field>
   <vaadin-button theme="primary" id="button_exit" style="align-self: flex-end;">
     Salir 
   </vaadin-button>
  </vaadin-horizontal-layout>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout theme="spacing" style="align-self: center;">
  <h2>Camiones en Muelles</h2>
 </vaadin-horizontal-layout>
 <vaadin-horizontal-layout theme="spacing" style="align-self: center; justify-content: center;">
  <vaadin-grid id="grid_trucks" style="align-self: center;"></vaadin-grid>
 </vaadin-horizontal-layout>
</vaadin-vertical-layout>
`;
  }

  static get is() {
    return 't-ft-view';
  }

  static get properties() {
    return {
      // Declare your properties here.
    };
  }
}

customElements.define(TFTView.is, TFTView);
