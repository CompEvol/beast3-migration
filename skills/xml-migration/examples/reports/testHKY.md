## XML Migration Report

- Files processed : 1
- Total changes   : 20
- ⚠ Warnings      : 6  (semantic replacements — review required)

### skills/xml-migration/examples/testHKY.xml  (12 renames, ⚠ 6 warnings)

    1. [info]    version: 2.0 → 2.8
    2. [info]    namespace: updated (deprecated classes use full FQNs; no spec packages in namespace)
    3. [rename]  spec= "HKY" → "beast.base.spec.evolution.substitutionmodel.HKY"
    4. [rename]  spec= "Frequencies" → "beast.base.spec.evolution.substitutionmodel.Frequencies"
    5. [rename]  spec= "SiteModel" → "beast.base.spec.evolution.sitemodel.SiteModel"
    6. [rename]  spec= "TreeLikelihood" → "beast.base.spec.evolution.likelihood.TreeLikelihood"
    7. [warn] ⚠  TreeLikelihood mapped to spec twin — consider beast.base.spec.evolution.likelihood.ThreadedTreeLikelihood for multi-core performance
    8. [rename]  spec= "RealParameter" (bare tag) → "beast.base.spec.inference.parameter.RealScalarParam"  domain="PositiveReal"  dropped: lower="0.0"
    9. [rename]  spec= "ClusterTree" → "beast.base.spec.evolution.tree.ClusterTree"
   10. [rename]  spec= "RandomTree" → "beast.base.spec.evolution.tree.coalescent.RandomTree"
   11. [rename]  spec= "ConstantPopulation" → "beast.base.spec.evolution.tree.coalescent.ConstantPopulation"
   12. [rename]  spec= "RealParameter" → "beast.base.spec.inference.parameter.RealScalarParam"  domain="Real"
   13. [warn] ⚠  spec= "ScaleOperator" [parameter=] → "ScaleOperator"  (class split — inference mode)
   14. [warn] ⚠  spec= "ScaleOperator" [tree=] → "ScaleTreeOperator"  (class split — evolution mode)
   15. [rename]  spec= "SubtreeSlide" → "beast.base.evolution.operator.kernel.BactrianSubtreeSlide"
   16. [warn] ⚠  SubtreeSlide attr gaussian="true" dropped — BactrianSubtreeSlide has no gaussian Input
   17. [warn] ⚠  spec= "Uniform" [tree=] → full legacy path required  (short name resolves to distribution, not tree operator)
   18. [rename]  spec= "ESS" → "beast.base.spec.inference.util.ESS"
   19. [rename]  spec= "ESS" → "beast.base.spec.inference.util.ESS"
   20. [warn] ⚠  spec= "Prior+OneOnX" (@hky.kappa) → "LogNormal"  M=1.0  S=0.5
