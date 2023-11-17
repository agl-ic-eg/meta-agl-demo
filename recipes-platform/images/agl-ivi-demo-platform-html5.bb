require agl-image-ivi.bb

SUMMARY = "DEMO platform of AGL HTML5 profile"
DESCRIPTION = "Contains the web runtime and sample web apps"

require agl-demo-container-guest-integration.inc

CLANGSDK = "1"

# add packages for demo platform (include demo apps) here
IMAGE_INSTALL += " \
    packagegroup-agl-demo-platform-html5 \
"
