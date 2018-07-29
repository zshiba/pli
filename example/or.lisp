(define or (lambda (p1 p2) (cond (p1 t)
                                 (p2 t)
                                 (t nil))))

(or t t)

(or t nil)

(or nil t)

(or nil nil)

(or ((lambda (x) (eq x (quote a))) (quote a))
    ((lambda (x) (eq x (quote b))) (quote b)))

(define pred1 (lambda (x) (atom x)))

(define pred2 (lambda (x) (cond ((atom x) nil)
                                (t t))))

(or (pred1 (quote a)) (pred2 (quote (b c))))

(or (pred1 (quote a)) (pred2 (quote b)))

(or (pred1 (quote (a b))) (pred2 (quote c)))
