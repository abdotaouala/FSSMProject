/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.Boncommande;

@FacesConverter(value = "bcConverter")
public class BoncommandeControllerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {

        if (value == null || value.length() == 0) {
            return null;
        } else {
            try {
                BoncommandeController controller = (BoncommandeController) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "boncommandeController");
                Object o = controller.getBoncommande(getKey(value));
                if (o == null) {
                    Boncommande bc = new Boncommande();
                    bc.setIdBC(Integer.parseInt(value));
                    return bc;
                } else {
                    return o;
                }
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Bon Commande."));
            }
        }
    }

    java.lang.Integer getKey(String value) {
        java.lang.Integer key;
        key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(java.lang.Integer value) {
        StringBuilder sb = new StringBuilder();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object != null) {
            Boncommande o = (Boncommande) object;
            return String.valueOf(((Boncommande) object).getIdBC());
        } else {
            return null;
        }
    }

}
