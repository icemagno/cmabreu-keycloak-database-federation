package br.com.cmabreu.dbuserprovider.model;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.adapter.AbstractUserAdapterFederatedStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAdapter extends AbstractUserAdapterFederatedStorage {

    private final String keycloakId;
    private       String username;
    private Logger logger = LoggerFactory.getLogger( UserAdapter.class );
    
    public UserAdapter(KeycloakSession session, RealmModel realm, ComponentModel model, Map<String, String> data, boolean allowDatabaseToOverwriteKeycloak) {
    	super(session, realm, model);
        this.keycloakId = StorageId.keycloakId(model, data.get("id"));
        this.username = data.get("username");
    	logger.info( "UserAdapter constructor, username={0}", this.username );
        try {
          Map<String, List<String>> attributes = this.getAttributes();
          for (Entry<String, String> e : data.entrySet()) {
              Set<String>  newValues = new HashSet<>();
              if (!allowDatabaseToOverwriteKeycloak) {
                List<String> attribute = attributes.get(e.getKey());
                if (attribute != null) {
                    newValues.addAll(attribute);
                }
              }
              newValues.add(StringUtils.trimToNull(e.getValue()));
              logger.info("  > " + e.getKey() );
              this.setAttribute(e.getKey(), newValues.stream().filter(Objects::nonNull).collect(Collectors.toList()));
          }
        } catch(Exception e) {
        	logger.error( e.getMessage() );
        }
    }


    @Override
    public String getId() {
        return keycloakId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

  

}
