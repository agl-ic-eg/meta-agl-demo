SUMMARY     = "AGL HTML5 Examples"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7ea5dd8751060d9f04d2748030c547ed"
APPINFO_FILE = "appinfo-examples.json"

require webapp-samples.inc

inherit pythonnative agl-app

AGL_APP_TEMPLATE = "agl-app-web"
AGL_APP_ID = "webapps-examples"
AGL_APP_NAME = "Examples"

do_install:append() {
  cp -R --no-dereference --preserve=mode,links ${S}/examples/* ${D}${WAM_APPLICATIONS_DIR}/${PN}
}

