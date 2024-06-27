SUMMARY     = "AGL HTML5 HVAC Application"
HOMEPAGE    = "https://git.automotivelinux.org/apps/html5-hvac/"
SECTION     = "apps"
LICENSE     = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PV      = "1.0+git${SRCPV}"
S       = "${WORKDIR}/git"
B       = "${WORKDIR}/build"

SRC_URI = " \
  git://gerrit.automotivelinux.org/gerrit/apps/html5-hvac;protocol=https;branch=master \
"
SRCREV = "c323ab8fde212120d8d1914d453afeb55b3576e5"

inherit agl-app

AGL_APP_TEMPLATE = "agl-app-web"
AGL_APP_ID = "webapps-hvac"
AGL_APP_NAME = "HTML5 HVAC"

DEPENDS = "nodejs-native protobuf-native icu-native"

do_compile[network] = "1"
do_compile() {
  cd ${S}
  rm -rf package node_modules package-lock.json
  npm install
  npm run generate-grpc
  npm run build
}

WAM_APPLICATIONS_DIR = "${libdir}/wam_apps"

do_install() {
  install -d ${D}${WAM_APPLICATIONS_DIR}/${PN}
  cp -R --no-dereference --preserve=mode,links ${S}/dist/* ${D}${WAM_APPLICATIONS_DIR}/${PN}
}

FILES:${PN} = "${WAM_APPLICATIONS_DIR}/${PN}"
