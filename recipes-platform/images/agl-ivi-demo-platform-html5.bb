require agl-image-ivi.bb

SUMMARY = "DEMO platform of AGL HTML5 profile"
DESCRIPTION = "Contains the web runtime and sample web apps"

# add packages for demo platform (include demo apps) here
IMAGE_INSTALL:append = " \
    packagegroup-agl-demo-platform-html5 \
"
