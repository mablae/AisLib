/* Copyright (c) 2011 Danish Maritime Authority.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.dma.ais.data;

import dk.dma.ais.message.AisMessage;
import dk.dma.ais.message.AisMessage18;
import dk.dma.ais.message.AisMessage19;
import dk.dma.ais.message.AisMessage24;

/**
 * Class to represent a class B vessel target
 */
public class AisClassBTarget extends AisVesselTarget {

    private static final long serialVersionUID = 1L;

    public AisClassBTarget() {
        super();
    }

    @Override
    public void update(AisMessage aisMessage) {
        // Throw error if message is from other type of target
        if (AisClassATarget.isClassAPosOrStatic(aisMessage) || AisBsTarget.isBsReport(aisMessage)
                || AisAtonTarget.isAtonReport(aisMessage)) {
            throw new IllegalArgumentException("Trying to update class B target with report of other target type");
        }
        // Ignore everything but class B pos and static reports (could be ASM messages etc)
        if (!isClassBPosOrStatic(aisMessage)) {
            return;
        }
        super.update(aisMessage);
    }

    public AisClassBPosition getClassBPosition() {
        return (AisClassBPosition) this.vesselPosition;
    }

    public AisClassBStatic getClassBStatic() {
        return (AisClassBStatic) this.vesselStatic;
    }

    /**
     * Determine if AIS message is class B position report or static report
     * 
     * @param aisMessage
     * @return
     */
    public static boolean isClassBPosOrStatic(AisMessage aisMessage) {
        return aisMessage instanceof AisMessage18 || aisMessage instanceof AisMessage24 || aisMessage instanceof AisMessage19;
    }

}
