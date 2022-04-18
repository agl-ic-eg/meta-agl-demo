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
  file://webapps-hvac.desktop \
"
SRCREV = "e64d69bdad0bb302900ec631b54ad6b6d6dcbacd"

DEPENDS = "nodejs-native"

do_compile() {
  cd ${S}
  rm -rf package node_modules package-lock.json
  npm install
  npm run build
}

WAM_APPLICATIONS_DIR="${libdir}/wam_apps"
APPLICATIONS_DIR="${datadir}/applications"

do_install() {
  install -d ${D}${WAM_APPLICATIONS_DIR}/${PN}
  cp -R --no-dereference --preserve=mode,links ${S}/dist/* ${D}${WAM_APPLICATIONS_DIR}/${PN}
  install -d ${D}${APPLICATIONS_DIR}
  install ${WORKDIR}/webapps-hvac.desktop ${D}${APPLICATIONS_DIR}
}

FILES_${PN} = " \
  ${WAM_APPLICATIONS_DIR}/${PN} \
  ${APPLICATIONS_DIR} \
"
