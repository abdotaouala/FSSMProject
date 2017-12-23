
package beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import model.Secteurprincipal;
@FacesConverter(value= "spConverter")
public class SecteurPrincipalContollerConverter implements Converter  {
@Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SecteurprincipalController controller = (SecteurprincipalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "secteurprincipalController");
            return controller.getSecteurprincipal(getKey(value));
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
            if (object == null) {
                return null;
            }
            if (object instanceof Secteurprincipal) {
                Secteurprincipal o = (Secteurprincipal) object;
                return getStringKey(o.getIdSecteurP());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Secteurprincipal.class.getName());
            }
        }
    
}
