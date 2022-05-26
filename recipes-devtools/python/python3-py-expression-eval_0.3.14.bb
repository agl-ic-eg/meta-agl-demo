SUMMARY = "Python Mathematical Expression Evaluator"
HOMEPAGE = "https://github.com/AxiaCore/py-expression-eval"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5a9126e7f56a0cf3247050de7f10d0f4"

# NOTE: Pulling from github.com instead of pypi.org as the tarfiles on
#       the latter do not include the LICENSE file.

SRC_URI = "git://github.com/axiacore/py-expression-eval.git;protocol=https;branch=master"
SRCREV = "e7cfbedb3cdb1c428ae3dfbc967fe43deffa5e64"

S = "${WORKDIR}/git"

inherit setuptools3
