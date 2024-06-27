require recipes-platform/images/agl-ivi-image.bb

SUMMARY = "AGL IVI demo HTML5 image"
DESCRIPTION = "Contains the web runtime and sample web apps"

CLANGSDK = "1"

# add packages for demo platform (include demo apps) here
IMAGE_INSTALL += " \
    packagegroup-agl-demo-platform-html5 \
"
