/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.core.command.runtime.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.drools.core.command.IdentifiableResult;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.impl.KnowledgeCommandContext;
import org.drools.core.impl.StatefulKnowledgeSessionImpl;
import org.drools.core.impl.StatefulKnowledgeSessionImpl.ObjectStoreWrapper;
import org.drools.core.reteoo.ReteooWorkingMemory;
import org.kie.command.Context;
import org.kie.runtime.KieSession;
import org.kie.runtime.ObjectFilter;

@XmlAccessorType(XmlAccessType.NONE)
public class GetObjectsCommand
    implements
    GenericCommand<Collection>, IdentifiableResult {

    public String getOutIdentifier() {
        return outIdentifier;
    }

    public void setOutIdentifier(String outIdentifier) {
        this.outIdentifier = outIdentifier;
    }

    private ObjectFilter filter = null;
    
    @XmlAttribute(name="out-identifier")
    private String outIdentifier;

    public GetObjectsCommand() {
    }

    public GetObjectsCommand(ObjectFilter filter) {
        this.filter = filter;
    }

    public GetObjectsCommand(ObjectFilter filter, String outIdentifier) {
        this.filter = filter;
        this.outIdentifier = outIdentifier;
    }

    public Collection execute(Context context) {
        KieSession ksession = ((KnowledgeCommandContext) context).getKieSession();
        
        Collection col = null;
        
        if ( filter != null ) {
            
            col =  ksession.getObjects( this.filter );
        } else {
            col =  ksession.getObjects( );
        }
        
        if ( this.outIdentifier != null ) {
            List objects = new ArrayList( col );
            
            ((StatefulKnowledgeSessionImpl)ksession).session.getExecutionResult().getResults().put( this.outIdentifier, objects );
        }
        
        return col;
    }
    
    public Collection< ? extends Object > getObjects(ReteooWorkingMemory session) {
        return new ObjectStoreWrapper( session.getObjectStore(),
                                       null,
                                       ObjectStoreWrapper.OBJECT );
    }

    public Collection< ? extends Object > getObjects(ReteooWorkingMemory session, org.kie.runtime.ObjectFilter filter) {
        return new ObjectStoreWrapper( session.getObjectStore(),
                                       filter,
                                       ObjectStoreWrapper.OBJECT );
    }

    public String toString() {
        if ( filter != null ) {
            return "session.iterateObjects( " + filter + " );";
        } else {
            return "session.iterateObjects();";
        }
    }

}
