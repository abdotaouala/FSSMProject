package beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import model.Compte;

@FacesConverter(value = "cptConverter")
public class CompteControllerConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        } else {
            try {
                CompteController controller = (CompteController) facesContext.getApplication().getELResolver().
                        getValue(facesContext.getELContext(), null, "compteController");
                Object o = controller.getCompte(getKey(value));
                if (o == null) {
                    Compte c = new Compte();
                    c.setIdCompte(Integer.parseInt(value));
                    return c;
                } else {
                    return o;
                }
            } catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
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
             if (object instanceof Compte && object!=null) {
                Compte o = (Compte) object;
                return getStringKey(o.getIdCompte());
            }else{
                return null;
            }
        }

}
