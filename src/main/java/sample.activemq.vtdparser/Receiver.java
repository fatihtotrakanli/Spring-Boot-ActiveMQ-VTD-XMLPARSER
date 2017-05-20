/*
 * Copyright 2012-2015 the original author or authors.
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

package sample.activemq.vtdparser;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class Receiver {

    @JmsListener(destination = "xml-queue")
    public void receiveQueue(Map map) throws IOException, InterruptedException {

        try {
            VTDGen vg = new VTDGen();
            vg.setDoc((byte[]) map.get("file"));
            vg.parse(false);
            VTDNav vn = vg.getNav();

            //Option 1 With AutoPilot
            if (map.get("fileName").equals("test.xml")) {
                AutoPilot ap = new AutoPilot(vn);
                ap.selectXPath("/purchaseOrder/item/cargo/*");
                while (ap.evalXPath() != -1) {
                    System.out.println("Option 1 : " + getElement(vn));
                }

            } else if (map.get("fileName").equals("test1.xml")) {
                //Option 2 sample with last child of xml
                if (vn.matchElement("customer")) {
                    if (vn.toElement(VTDNav.LAST_CHILD, "information")) {
                        if (vn.toElement(VTDNav.FIRST_CHILD)) {
                            do {
                                System.out.println("Option 2 : " + vn.toString(vn.getText()));
                            } while (vn.toElement(VTDNav.NEXT_SIBLING));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("exception occurred ==>" + e);
        }

    }

    public static String getElement(VTDNav vn) throws NavException {
        int first = (int) vn.getElementFragment();
        int second = (int) (vn.getElementFragment() >> 32);
        return new String(vn.getXML().getBytes(first, second));
    }
}
