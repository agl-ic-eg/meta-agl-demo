# This layer contains the WIP feature/demo code only
#---------------------------------------------------
BBLAYERS =+ " \
   ${METADIR}/meta-agl-demo \
"

AGL_META_FILESYSTEMS = "${METADIR}/external/meta-openembedded/meta-filesystems"
AGL_META_MULTIMEDIA = "${METADIR}/external/meta-openembedded/meta-multimedia"
AGL_META_NETWORKING = "${METADIR}/external/meta-openembedded/meta-networking"
AGL_META_PYTHON = "${METADIR}/external/meta-openembedded/meta-python"

# Needed to satisfy uutils-coreutils DEPENDS when using SELinux
AGL_META_CLANG = "${METADIR}/external/meta-clang"

# OpenEmbedded layer to add support for multiple cloud service provider solutions
#---------------------------------------------------
BBLAYERS =+ " \
   ${METADIR}/external/meta-qt5 \
"
