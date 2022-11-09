SUMMARY     = "AGL HTML5 Youtube"
LICENSE     = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7ea5dd8751060d9f04d2748030c547ed"
APPINFO_FILE = "appinfo-youtube.json"

require webapp-samples.inc

inherit pythonnative agl-app

AGL_APP_TEMPLATE = "agl-app-web"
AGL_APP_ID = "webapps-youtube"
AGL_APP_NAME = "Youtube"

do_install:append() {
  cp -R --no-dereference --preserve=mode,links ${S}/youtube/* ${D}${WAM_APPLICATIONS_DIR}/${PN}
}

