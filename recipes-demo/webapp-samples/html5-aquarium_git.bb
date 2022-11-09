SUMMARY     = "AGL HTML5 Aquarium Demo"
LICENSE     = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://aquarium/LICENSE;md5=9f0d95e61aa217eacd61ee6833cf411c"
APPINFO_FILE = "appinfo-aquarium.json"

require webapp-samples.inc

inherit pythonnative agl-app

AGL_APP_TEMPLATE = "agl-app-web"
AGL_APP_ID = "webapps-aquarium"
AGL_APP_NAME = "Aquarium"

do_install:append() {
  cp -R --no-dereference --preserve=mode,links ${S}/aquarium/* ${D}${WAM_APPLICATIONS_DIR}/${PN}
}

