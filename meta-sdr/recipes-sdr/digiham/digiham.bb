SUMMARY = "tools for decoding digital ham communication ."

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

SRC_URI = " \
  git://github.com/jketterl/digiham.git;protocol=https;branch=develop \
"
SRCREV = "410853cf284809b0bd2f02f56d80bd7909bd8f74"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

DEPENDS = "csdr codecserver icu"

RDEPENDS:${PN} = "codecserver"

do_configure:prepend() {
    export CMAKE_PREFIX_PATH="${STAGING_DIR_TARGET}${libdir}/cmake/CodecServer:${CMAKE_PREFIX_PATH}"
}

FILES:${PN} = " \
    ${bindir}/digiham \
    ${sysconfdir}/codecserver \
"
