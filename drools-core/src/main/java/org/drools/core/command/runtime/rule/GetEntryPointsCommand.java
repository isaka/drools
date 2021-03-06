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

import java.util.Collection;

import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.impl.KnowledgeCommandContext;
import org.kie.command.Context;
import org.kie.runtime.KieSession;
import org.kie.runtime.rule.SessionEntryPoint;

public class GetEntryPointsCommand
    implements
    GenericCommand<Collection< ? extends SessionEntryPoint>> {

    public GetEntryPointsCommand() {
    }

    public Collection< ? extends SessionEntryPoint> execute(Context context) {
        KieSession ksession = ((KnowledgeCommandContext) context).getKieSession();
        return ksession.getEntryPoints();
    }

    public String toString() {
        return "session.getEntryPoints( );";
    }
}
