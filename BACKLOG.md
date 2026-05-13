# BEAST 2 → BEAST 3 migration backlog

Auto-generated from CBAN's `packages2.7.xml`, `packages-extra-2.7.xml`,
and `packages2.8.xml`. Regenerate with `python3 scripts/gen-backlog.py`.

_Last regenerated: 2026-05-13T17:39:18+12:00_

## Summary

- v2.7 packages (union of main + extra): **84**
- Migrated to v2.8 (in `packages2.8.xml`): **10**
- Built into beast3 core in v2.8: **2** (BEAST.app, BEAST.base)
- Tracked in `packages.yaml` but not yet in `packages2.8.xml`: **3**
- Untracked, unmigrated: **69**

## Tracked in `packages.yaml` but not yet in CBAN packages2.8.xml

These packages are being worked on locally but have not yet appeared in `packages2.8.xml`.

| Package (CBAN name) | v2.7 source | Latest 2.7 | packages.yaml stage | GitHub |
|---|---|---|---|---|
| bModelTest | main | 1.3.3 | compile_tested | [`BEAST2-Dev/bModelTest`](https://github.com/BEAST2-Dev/bModelTest) |
| FLC | main | 1.2.0 | maven_central | [`4ment/flc`](https://github.com/4ment/flc) |
| OBAMA | main | 1.1.1 | compile_tested | [`rbouckaert/obama`](https://github.com/rbouckaert/obama) |

## Untracked — needs migration work and/or addition to packages.yaml

These v2.7 packages are not in `packages2.8.xml` and are not currently tracked in this repo.

| Package | v2.7 source | Latest 2.7 | projectURL | Description |
|---|---|---|---|---|
| 5rf | extra | 0.0.2 | [link](https://github.com/rbouckaert/5rf/) | Rate Class Root Frequency Model |
| AlmostDistributions | extra | 0.2.1 | [link](https://github.com/rbouckaert/AlmostDistributions) | Almost-distributions |
| ASM | extra | 0.0.1 | [link](https://github.com/rbouckaert/asm) | Automatic stopping MCMC |
| Babel | main | 0.4.2 | [link](https://github.com/rbouckaert/Babel) | BABEL = BEAST analysis backing effective linguistics |
| bacter | main | 3.0.1 | [link](https://tgvaughan.github.io/bacter) | Bacterial ARG inference. |
| BADTRIP | main | 2.0.0 | [link](https://bitbucket.org/nicofmay/badtrip) | Infer transmission time for non-haplotype data and epi data |
| BASTA | main | 4.0.0 | [link](https://bitbucket.org/nicofmay/basta-bayesian-structured-coalescent-approximation) | Bayesian structured coalescent approximation |
| bdmm | main | 2.0.0 | [link](https://github.com/denisekuehnert/bdmm) | Multitype birth-death model (aka birth-death-migration model) |
| BDSKY | main | 1.5.1 | [link](https://github.com/BEAST2-Dev/bdsky/) | birth death skyline - handles serially sampled tips, piecewise constant rate changes through time and sampled ancestors |
| bdtree | main | 0.0.2 | [link](https://github.com/fkmendes/bdtree) | Birth-death sequential sampling |
| BEAST2Lang | extra | 0.0.1 | [link](https://github.com/CompEvol/beast2lang/) | Run a Beast2 model using the definition language |
| beastmap | extra | 0.0.3 | [link](https://github.com/jordandouglas/BeastMap/) | Stochastic mapping and ancestral sequence/state reconstruction |
| BEASTvntr | main | 0.2.0 | [link](https://github.com/arjun-1/BEASTvntr) | Variable Number of Tandem Repeat data, such as microsattelites |
| BICEPS | main | 1.1.2 | [link](https://github.com/rbouckaert/biceps/) | Bayesian Integrated Coalescent Epoch PlotS + Yule Skyline |
| BREAK_AWAY | main | 1.2.0 | [link](https://github.com/rbouckaert/break-away) | break-away model of phylogegraphy |
| BREATH | extra | 0.0.5 | [link](https://github.com/rbouckaert/transmission) | Bayesian Reconstruction and Evolutionary Analysis of Transmission Histories |
| CA | main | 2.1.0 | [link](https://github.com/BEAST2-Dev/cladeage) | Bayesian estimation of clade ages based on probabilities of fossil sampling |
| CCD | main+extra | 1.0.3 | [link](https://github.com/CompEvol/CCD) | Better tree distribution and point estimates, computation of entropy, rogues and more. |
| ClaDS | main | 2.3.0 | [link](https://bitbucket.org/bjoelle/clads/) | Implementation of the ClaDS birth-death tree prior with sampled ancestors |
| CoalRe | main | 1.0.4 | [link](https://github.com/nicfel/CoalRe/) | Inference of Recombination networks |
| CodonSubstModels | main | 2.0.1 | [link](https://github.com/BEAST2-Dev/codonsubstmodels) | Codon substitution models for DNA |
| contacTrees | main | 1.2.0 | [link](https://github.com/NicoNeureiter/contacTrees) | Phylogenetic model with horizontal transfer for linguistics |
| contraband | main | 1.0.2 | [link](https://github.com/fkmendes/contraband) | Scalable brownian models for continuous trait evolution |
| CorrelatedTrait | extra | 0.0.5 | [link](https://github.com/rbouckaert/CorrelatedTrait) | Analysis for a fixed tree or fixed tree set |
| CubeVB | main | 1.0.2 | [link](https://github.com/rbouckaert/cubevb) | Variational Bayes on a cube |
| DENIM | main | 1.1.1 | [link](http://indriid.com/software.html) | Divergence Estimation Notwithstanding ILS and Migration |
| EpiInf | main | 8.0.0 | [link](http://tgvaughan.github.io/EpiInf) | BD/SIR/SIS epidemic trajectory inference. |
| EpochClockModel | extra | 0.0.2 | [link](https://github.com/rbouckaert/EpochClockModel) | Add clock models that have different mean rates at different epochs |
| Experimenter | extra | 0.2.0 | [link](https://github.com/christiaanjs/beast-validation/) | Experimenter |
| FastRelaxedClockLogNormal | main | 1.2.0 | [link](https://github.com/Rong419/ConstantDistanceOperator) | Relaxed clock that works well with large data |
| FixedTreeAnalysis | main | 0.0.2 | [link](https://github.com/rbouckaert/FixedTreeAnalysis/) | Analysis for a fixed tree or fixed tree set |
| FoldBeast | extra | 0.0.2 | [link](https://github.com/jordandouglas/FoldBeast/) | 3Di characters in phylogenetics |
| FreeRateModel | main | 1.0.0 | [link](https://github.com/BEAST2-Dev/FreeRateModel) | Non-parametric heterogeneous site model |
| GEO_SPHERE | main | 1.4.2 | [link](https://github.com/BEAST2-Dev/beast-geo) | Whole world phylogeography |
| HetMM | extra | 0.0.1 | [link](https://github.com/jordandouglas/HetMM) | Heterogeneous Michaelis-Menten model |
| IndelDollo | extra | 0.0.1 | [link](https://github.com/jordandouglas/IndelDollo/) | Mutations, insertions, and deletions |
| lphybeast | main | 1.3.0 | [link](https://github.com/LinguaPhylo/LPhyBeast) | A command-line program that takes an LPhy model specification including a data block,  	                  and produce... |
| LPhyBeastExt | main | 1.0.0 | [link](https://github.com/LinguaPhylo/LPhyBeastExt) | An extension of LPhyBEAST. |
| MASTER | main | 7.0.0 | [link](http://tgvaughan.github.io/MASTER) | Stochastic population dynamics simulation |
| MGSM | main | 0.4.0 | [link](https://github.com/BEAST2-Dev/MGSM/wiki) | Multi-gamma and relaxed gamma site models |
| MODEL_SELECTION | main | 1.6.2 | [link](https://github.com/BEAST2-Dev/model-selection) | Select models through path sampling/stepping stone analysis |
| MSBD | main | 2.0.6 | [link](https://bitbucket.org/bjoelle/msbd/) | MSBD multi-type birth-death tree prior with sampled ancestors |
| MultiTypeTree | main | 8.3.0 | [link](https://tgvaughan.github.io/MultiTypeTree) | Structured coalescent inference. |
| NS | main | 1.2.2 | [link](https://github.com/BEAST2-Dev/nested-sampling/wiki) | Nested sampling for model selection and posterior inference |
| PhyDyn | main | 1.4.0 | [link](https://github.com/mrc-ide/PhyDyn) | PhyDyn: Epidemiological modelling with BEAST |
| phylodynamics | main | 1.4.0 | [link](https://github.com/BEAST2-Dev/phylodynamics) | BDSIR and Stochastic Coalescent |
| phylonco | main | 1.2.1 | [link](https://github.com/bioDS/beast-phylonco) | A package for single cell cancer evolution. |
| phylonco.lphybeast | main | 1.2.1 | [link](https://github.com/bioDS/beast-phylonco) | A LPhyBEAST extension for phylonco (requires: LPhy and phylonco-lphy). |
| PIQMEE | main | 1.0.0 | [link](https://github.com/boskovav/piqmee) | Birth-death skyline-based method efficiently dealing with duplicate sequences |
| PoMo | main | 1.1.1 | [link](https://bitbucket.org/nicofmay/pomo-beast) | PoMo, a substitution model that separates mutation and drift processes |
| PopFunc | main | 0.0.1 | [link](https://github.com/LinguaPhylo/PopFunc) | A package for Bayesian model averaging of parametric coalescent models in phylodynamic inference. |
| RBS | extra | 1.5.2 | [link](https://github.com/BEAST2-Dev/rb-beast/) | AutoPartition and Reverisble Jump Based substitution model |
| Recombination | main | 1.0.1 | [link](https://github.com/nicfel/Recombination/) | Inference of Recombination networks |
| resub | main+extra | 1.0.0 | [link](https://github.com/jordandouglas/resub) | Refinement expansion substitution model |
| rootfreqs | extra | 0.0.2 | [link](https://github.com/rbouckaert/rootfreqs) | Allows root frequencies to be specified per site. |
| SCOTTI | main | 3.0.0 | [link](https://bitbucket.org/nicofmay/scotti) | Structured COalescent Transmission Tree Inference |
| SNAPP | main | 1.6.1 | [link](http://beast2.org/snapp/) | SNP and AFLP Phylogenies |
| snapper | main | 1.1.5 | [link](https://github.com/rbouckaert/snapper) | Diffusion based SNP and AFLP Phylogenies |
| SpeciesNetwork | main | 1.0.0 | [link](https://github.com/zhangchicool/speciesnetwork) | Multispecies network coalescent (MSNC) inference of introgression and hybridization |
| speedemon | main | 1.1.0 | [link](https://github.com/rbouckaert/speedemon) | Fast species delimitation under the multispecies coalescent |
| SSM | main | 1.2.2 | [link](https://github.com/BEAST2-Dev/substmodels/) | Standard Nucleotide Substitution Models |
| STACEY | main | 1.3.1 | [link](http://indriid.com/software.html) | Species delimitation and species tree estimation |
| StarBEAST2 | main | 1.0.0 | [link](https://github.com/genomescale/starbeast2) | Multispecies coalescent inference using multi-locus and fossil data |
| starbeast3 | main | 1.2.1 | [link](https://github.com/rbouckaert/starbeast3) | StarBeast3 multispecies coalescent using advanced MCMC operators |
| TargetedBeast | extra | 1.0.0 | [link](https://github.com/nicfel/targetedbeast/) | Targeted MCMC operators for Improving the Scalability of Bayesian Phylodynamic Inference |
| TiDeTree | main | 1.0.1 | [link](https://github.com/seidels/tidetree) | Estimating single-cell trees from lineage tracing data |
| timtam | main | 0.4.0 | [link](https://aezarebski.github.io/timtam/index.html) | Distribution for birth-death models with occurrence data and population size estimation. |
| TreeStat2 | main | 0.2.0 | [link](https://github.com/alexeid/TreeStat2/) | Utility for calculating tree statistics from tree log file |
| TyCHE | extra | 0.0.10 | [link](https://tyche.readthedocs.io) | Type-linked Clocks for Heterogenous Evolution |

## Already migrated to v2.8 (for reference)

| Package | Latest 2.7 | Latest 2.8 |
|---|---|---|
| BEAST_CLASSIC | 1.6.4 | 1.7.0 |
| BEASTLabs | 2.0.3 | 2.1.0 |
| CoupledMCMC | 1.2.2 | 1.3.0 |
| feast | 10.6.1 | 11.0.0 |
| gammaspike | 1.2.0 | 1.3.0 |
| Mascot | 3.0.7 | 3.1.0 |
| MM | 1.2.1 | 1.3.0 |
| ORC | 1.2.1 | 1.3.1 |
| ReMASTER | 2.7.4 | 3.0.0 |
| SA | 2.1.1 | 2.3.0 |
