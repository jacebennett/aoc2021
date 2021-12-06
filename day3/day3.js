const fs = require('fs');

const text = fs.readFileSync("input.txt", 'utf-8');
const input = text.split("\n").filter(i => i.length);

console.log(input.length)

const mostCommonChar = (col, strings) => {
  let counts = {"0": 0, "1": 0 };
  strings.forEach(s => {
    const char = s[col];
    counts[char] += 1
  });

  if (counts["0"] > counts["1"]) {
      return "0"
  }

  return "1"
}

const leastCommonChar = (col, strings) => {
  const opposite = mostCommonChar(col, strings);

  if (opposite === "1") {
      return "0"
  }
  return "1"
}

let prefix = ""
let candidates = input;
while (candidates.length > 1) {
  console.log({ prefix, candidates: candidates.length})
  const column = prefix.length;
  const nextChar = leastCommonChar(column, candidates)
  prefix += nextChar
  candidates = candidates.filter(c => c.startsWith(prefix))
}

console.log(candidates);