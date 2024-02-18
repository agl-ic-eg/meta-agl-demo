# Mark package as machine specific, which the upstream stub recipe
# does not do for unclear reasons.
PACKAGE_ARCH = "${MACHINE_ARCH}"

# Allow an empty package as results from the upstream stub recipe.
# This allows using the package without needing machine specific
# hooks.
ALLOW_EMPTY:${PN} = "1"
