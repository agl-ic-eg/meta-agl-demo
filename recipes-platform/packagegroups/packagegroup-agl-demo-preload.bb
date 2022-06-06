SUMMARY = "Extra software and configuration for tradeshow demo with AGL IVI profile demo platform"
LICENSE = "MIT"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = " \
    packagegroup-agl-demo-preload \
"

# NOTES:
# - demo-i2x-udev-conf:
#     Adds udev configuration and scripts for supporting USB attached
#     I2C devices for RTC and HVAC LED support.
# - sllin-demo:
#     Adds systemd unit and scripting to drive configuring sllin
#     driver with lin-config utility, enabling HVAC fan control and
#     steering wheel controls in the demo.
# - simple-can-simulator:
#     CAN message simulator for vehicle and engine speed message
#     generation, with some support for the cruise control events from
#     the steering wheel used in the demo setup.
#
RDEPENDS:${PN} = "\
    packagegroup-agl-demo-platform \
    demo-i2c-udev-conf \
    sllin-demo \
    simple-can-simulator \
"
