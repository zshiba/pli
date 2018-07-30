(define a (quote a))

(define b (quote b))

((lambda (x) (cons x (cons a (cons b nil)))) (quote x))
