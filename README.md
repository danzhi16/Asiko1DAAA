# Divide-and-Conquer Algorithms: Metrics & Analysis

## Architecture Notes

- **Recursion Depth**: Tracked by `DepthCounter` using thread-local counters. Each recursive call increases depth, and the maximum is recorded.
- **Operation Counting**: `OpCounter` counts key operations (comparisons, etc.) for each algorithm.
- **Memory Allocations**: Memory usage is measured before and after each run using `Runtime.getRuntime()`.
- **CSV Output**: All metrics are written to `results.csv` in semicolon-separated format for Excel compatibility.

## Recurrence Analysis and Proofs

### MergeSort

- **Recurrence:**  
  \( T(n) = 2T(n/2) + cn \)
- **Proof:**  
  This matches the Master Theorem with \( a=2, b=2, f(n)=\Theta(n) \).  
  Since \( f(n) = \Theta(n^{\log_2 2}) = \Theta(n) \), this is **Case 2**.  
  **Result:**  
  \( T(n) = \Theta(n \log n) \)

### QuickSort (Randomized, smaller-first recursion)

- **Recurrence (average case):**  
  \( T(n) = T(k) + T(n-k-1) + cn \), with expected \( k \approx n/2 \)
- **Proof:**  
  The expected split is balanced, so by linearity of expectation,  
  \( T(n) \approx 2T(n/2) + cn \)  
  By the Master Theorem (as above),  
  \( T(n) = \Theta(n \log n) \)  
  (Worst case is \( O(n^2) \), but randomization avoids this.)

### Deterministic Select (Median-of-Medians)

- **Recurrence:**  
  \( T(n) \leq T(\lceil n/5 \rceil) + T(7n/10) + cn \)
- **Proof:**  
  This fits the Akra-Bazzi Theorem.  
  Let \( T(n) = T(n/5) + T(7n/10) + cn \).  
  The solution is \( T(n) = O(n) \) (see CLRS, Section 9.3, or Akra-Bazzi examples).  
  **Result:**  
  \( T(n) = O(n) \)

### Closest Pair of Points (2D)

- **Recurrence:**  
  \( T(n) = 2T(n/2) + cn \)
- **Proof:**  
  This is identical to MergeSort, so by the Master Theorem Case 2,  
  \( T(n) = \Theta(n \log n) \)

## Plots

- **Time vs n:** Shows \( n \log n \) or \( n \) scaling as predicted.
- **Depth vs n:** Confirms logarithmic recursion depth.
- **Constant-Factor Effects:** JVM warmup, garbage collection, and cache effects are visible in timing and allocation metrics.

## Summary

- **Theory vs Measurement:** Empirical results match theoretical predictions for time and depth. Minor mismatches are due to JVM and system overhead.
- **CSV Format:** Results are exported as  
  `algorithmName;runTimeNanos;counter;depth;allocationBytes;n`

---

See `results.csv` for raw data and plots.