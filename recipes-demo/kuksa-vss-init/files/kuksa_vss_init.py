#!/usr/bin/env python3
# Copyright (c) 2022 Aakash Solanki, tech2aks@gmail.com
#
# Permission is hereby granted, free of charge, to any person obtaining a copy of
# this software and associated documentation files (the "Software"), to deal in
# the Software without restriction, including without limitation the rights to
# use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
# of the Software, and to permit persons to whom the Software is furnished to do
# so, subject to the following conditions:
#
# The above copyright notice and this permission notice shall be included in all
# copies or substantial portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.

import kuksa_viss_client
import time


class VSS:
    def __init__(self, client):
        self.client = client

        self.speed = "Vehicle.Speed"
        self.engineRPM = "Vehicle.Powertrain.CombustionEngine.Speed"
        self.fuelLevel = "Vehicle.Powertrain.FuelSystem.Level"
        self.coolantTemp = "Vehicle.Powertrain.CombustionEngine.ECT"
        self.leftIndicator = "Vehicle.Body.Lights.IsLeftIndicatorOn"
        self.rightIndicator = "Vehicle.Body.Lights.IsRightIndicatorOn"
        #   // Selected Gear output = > 0 = Neutral, 1/2/.. = Forward, -1/.. = Reverse, 126 = Park, 127 = Drive
        self.selectedGear = "Vehicle.Powertrain.Transmission.SelectedGear"
        self.lowBeamOn = "Vehicle.Body.Lights.IsLowBeamOn"
        self.highBeamOn = "Vehicle.Body.Lights.IsHighBeamOn"
        self.parkingLightOn = "Vehicle.Body.Lights.IsParkingOn"
        self.hazardLightOn = "Vehicle.Body.Lights.IsHazardOn"
        self.travelledDistance = "Vehicle.TravelledDistance"
        self.trunkLocked = "Vehicle.Body.Trunk.Rear.IsLocked"
        self.trunkOpen = "Vehicle.Body.Trunk.Rear.IsOpen"
        #   // \"normal\", \"sport\", \"economy\", \"snow\", \"rain\"]
        self.performanceMode = "Vehicle.Powertrain.Transmission.PerformanceMode"
        self.ambientAirTemperature = "Vehicle.Exterior.AirTemperature"
        self.mil = "Vehicle.OBD.Status.IsMILOn"
        self.cruiseControlError = "Vehicle.ADAS.CruiseControl.IsError"
        self.cruiseControlSpeedSet = "Vehicle.ADAS.CruiseControl.SpeedSet"
        self.cruiseControlisActive = "Vehicle.ADAS.CruiseControl.IsActive"
        self.batteryChargingStatus = "Vehicle.Powertrain.TractionBattery.Charging.IsCharging"
        # 
        self.currLat = "Vehicle.Cabin.CurrentLocation.Latitude"
        self.currLng = "Vehicle.Cabin.CurrentLocation.Longitude"
        self.desLat = "Vehicle.Cabin.Infotainment.Navigation.DestinationSet.Latitude"
        self.desLng = "Vehicle.Cabin.Infotainment.Navigation.DestinationSet.Longitude"
        self.steeringInfo = "Vehicle.Cabin.SteeringWheel.Switches.Info"

    def setInitialValues(self):
        print("Setting values")
        self.client.setValue(self.speed, '5')
        self.client.setValue(self.engineRPM, '1000')
        self.client.setValue(self.fuelLevel, '50')
        self.client.setValue(self.coolantTemp, '70')
        self.client.setValue(self.leftIndicator, "false")
        self.client.setValue(self.rightIndicator, "false")
        self.client.setValue(self.selectedGear, '127')
        self.client.setValue(self.lowBeamOn, "true")
        self.client.setValue(self.highBeamOn, "false")
        self.client.setValue(self.parkingLightOn, "true")
        self.client.setValue(self.hazardLightOn, "false")
        self.client.setValue(self.travelledDistance, '100')
        self.client.setValue(self.trunkLocked, "true")
        self.client.setValue(self.trunkOpen, "false")
        self.client.setValue(self.performanceMode, "normal")
        self.client.setValue(self.ambientAirTemperature, '28')
        self.client.setValue(self.mil, "false")
        self.client.setValue(self.cruiseControlError, "false")
        self.client.setValue(self.cruiseControlisActive, "false")
        self.client.setValue(self.cruiseControlSpeedSet, '60')
        self.client.setValue(self.batteryChargingStatus, "true")
        # 
        self.client.setValue(self.currLat, "31.708643")
        self.client.setValue(self.currLng, "76.931882")
        self.client.setValue(self.desLat, "31.781456")
        self.client.setValue(self.desLng, "76.997469")
        # Show the map
        self.client.setValue(self.steeringInfo, "false")
        print("All value set succesfully")


def main():
    config = {"ip": "localhost", "port": 8090, "insecure": False}
    client = kuksa_viss_client.KuksaClientThread(config)
    client.start()
    token_file = open(
    "/usr/lib/python3.10/site-packages/kuksa_certificates/jwt/all-read-write.json.token", "r")
    token = token_file.read()
    client.authorize(token, timeout=2)

    vss = VSS(client)

    time.sleep(2)

    vss.setInitialValues()
    client.stop()


if __name__ == '__main__':
    main()
